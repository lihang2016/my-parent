
package com.my.common.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Set;

public interface CreateMapper<T> {
	@InsertProvider(type = CreateProvider.class, method = "dynamicSQL")
	void create(T o);
	
	class CreateProvider extends BaseInsertProvider {
		public CreateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
			super(mapperClass, mapperHelper);
		}
		
		public String create(MappedStatement ms) {
			Class<?> entityClass = getEntityClass(ms);
			StringBuilder sql = new StringBuilder();
			//获取全部列
			Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
			//Identity列只能有一个
			Boolean hasIdentityKey = false;
			//先处理cache或bind节点
			for (EntityColumn column : columnList) {
				if (isSkip(column)) {
					continue;
				}
				if (StringUtil.isNotEmpty(column.getSequenceName())) {
				} else if (column.isIdentity()) {
					//这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
					//这是一个bind节点
					sql.append(SqlHelper.getBindCache(column));
					//如果是Identity列，就需要插入selectKey
					//如果已经存在Identity列，抛出异常
					if (hasIdentityKey) {
						//jdbc类型只需要添加一次
						if (column.getGenerator() != null && column.getGenerator().equals("JDBC")) {
							continue;
						}
						throw new RuntimeException(
							ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
					}
					//插入selectKey ,处理insert之后返回id问题
					newSelectKeyMappedStatement(ms, column);
					hasIdentityKey = true;
				} else if (column.isUuid()) {
					//uuid的情况，直接插入bind节点
					sql.append(SqlHelper.getBindValue(column, getUUID()));
				}
			}
			sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
			sql.append(insertColumns(entityClass, false, false, false));
			sql.append("<trim prefix=\"VALUES(\" suffix=\")\" suffixOverrides=\",\">");
			for (EntityColumn column : columnList) {
				if (isSkip(column)) {
					continue;
				}
				//优先使用传入的属性值,当原属性property!=null时，用原属性
				//自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
				if (column.isIdentity()) {
					sql.append(SqlHelper.getIfCacheNotNull(column, column.getColumnHolder(null, "_cache", ",")));
				} else {
					//其他情况值仍然存在原property中
					sql.append(SqlHelper.getIfNotNull(column, column.getColumnHolder(null, null, ","), isNotEmpty()));
				}
				//当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
				//序列的情况
				if (StringUtil.isNotEmpty(column.getSequenceName())) {
					sql.append(SqlHelper.getIfIsNull(column, getSeqNextVal(column) + " ,", false));
				} else if (column.isIdentity()) {
					sql.append(SqlHelper.getIfCacheIsNull(column, column.getColumnHolder() + ","));
				} else if (column.isUuid()) {
					sql.append(SqlHelper.getIfIsNull(column, column.getColumnHolder(null, "_bind", ","), isNotEmpty()));
				} else {
					//当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
					sql.append(SqlHelper.getIfIsNull(column, column.getColumnHolder(null, null, ","), isNotEmpty()));
				}
			}
			sql.append("</trim>");
			return sql.toString();
		}
		public static String insertColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
			StringBuilder sql = new StringBuilder();
			sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
			//获取全部列
			Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
			//当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
			for (EntityColumn column : columnList) {
				if (isSkip(column)) {
					continue;
				}
				if (skipId && column.isId()) {
					continue;
				}
				if (notNull) {
					sql.append(SqlHelper.getIfNotNull(column, column.getColumn() + ",", notEmpty));
				} else {
					sql.append(column.getColumn() + ",");
				}
			}
			sql.append("</trim>");
			return sql.toString();
		}
		public static boolean isSkip(EntityColumn column) {
			if (!column.isInsertable()) {
				return true;
			}
			String prop = column.getProperty();
			if (prop.equals("createTime") || prop.equals("updateTime") || prop.equals("version")||prop.equals("id")) {
				return true;
			}
			return false;
		}
	}
}
