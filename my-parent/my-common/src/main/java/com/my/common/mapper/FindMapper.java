
package com.my.common.mapper;

import com.google.common.collect.Maps;
import com.my.common.mapper.filter.SearchFilter;
import com.my.common.mapper.filter.SearchFilterParser;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.List;
import java.util.Map;

public interface FindMapper<T> {
	@SelectProvider(type = FindProvider.class, method = "dynamicSQL")
	List<T> find(String property, Object value);
	
	@SelectProvider(type = FindProvider.class, method = "dynamicSQL")
	T findOne(String property, Object value);

	@SelectProvider(type = FindProvider.class, method = "dynamicSQL")
	Page<T> findAllPage(Map<String, Object> map, Pageable pageable);

	class FindProvider extends MapperTemplate {
		
		public FindProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
			super(mapperClass, mapperHelper);
		}
		
		public void find(MappedStatement ms) {
			final Class<?> entityClass = getEntityClass(ms);
			//将返回值修改为实体类型
			setResultType(ms, entityClass);
			StringBuilder sql = new StringBuilder();
			sql.append(SqlHelper.selectAllColumns(entityClass));
			sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
			Map<String, Class<?>> fieldsTypes = Maps.newHashMap();
			EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
			for (EntityColumn column : entityTable.getEntityClassColumns()) {
				fieldsTypes.put(column.getProperty(), column.getJavaType());
			}
			FindSqlSource findSqlSource = new FindSqlSource(ms, entityClass, sql.toString(), fieldsTypes, false);
			setSqlSource(ms, findSqlSource);
		}
		
		public void findOne(MappedStatement ms) {
			final Class<?> entityClass = getEntityClass(ms);
			//将返回值修改为实体类型
			setResultType(ms, entityClass);
			StringBuilder sql = new StringBuilder();
			sql.append(SqlHelper.selectAllColumns(entityClass));
			sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
			Map<String, Class<?>> fieldsTypes = Maps.newHashMap();
			EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
			for (EntityColumn column : entityTable.getEntityClassColumns()) {
				fieldsTypes.put(column.getProperty(), column.getJavaType());
			}
			FindSqlSource findSqlSource = new FindSqlSource(ms, entityClass, sql.toString(), fieldsTypes, true);
			setSqlSource(ms, findSqlSource);
		}
		public void findAllPage(MappedStatement ms) {
			final Class<?> entityClass = getEntityClass(ms);
			//将返回值修改为实体类型
			setResultType(ms, entityClass);
			StringBuilder sql = new StringBuilder();
			sql.append(SqlHelper.selectAllColumns(entityClass));
			sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
			Map<String, Class<?>> fieldsTypes = Maps.newHashMap();
			EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
			for (EntityColumn column : entityTable.getEntityClassColumns()) {
				fieldsTypes.put(column.getProperty(), column.getJavaType());
			}
			FindAllSqlSource findSqlSource = new FindAllSqlSource(ms, sql.toString(), fieldsTypes);
			setSqlSource(ms, findSqlSource);
		}
	}
	class FindAllSqlSource implements SqlSource {
		private MappedStatement ms;
		private String sql;
		private Map<String, Class<?>> fieldsTypes;

		public FindAllSqlSource(MappedStatement ms, String sql, Map<String, Class<?>> fieldsTypes) {
			this.ms = ms;
			this.sql = sql;
			this.fieldsTypes = fieldsTypes;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			Assert.isInstanceOf(MapperMethod.ParamMap.class, parameterObject);
			MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
			Map<String, Object> map = (Map<String, Object>) paramMap.get("param1");
			Pageable pageable = (Pageable) paramMap.get("param2");
			StringBuilder sqlResult = new StringBuilder();
			sqlResult.append(sql);
			if (map != null && !map.isEmpty()) {
				sqlResult.append(" WHERE ");
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					SearchFilter searchFilter = SearchFilter.parse(entry.getKey(), entry.getValue());
					if (searchFilter == null) {
						continue;
					}
					Class proType = fieldsTypes.get(searchFilter.fieldName);
					Assert.notNull(proType, "属性[" + searchFilter.fieldName + "]不存在");
					sqlResult.append(SearchFilterParser.parseSqlField(searchFilter, proType));
					sqlResult.append(" AND ");
				}
				sqlResult.delete(sqlResult.length() - 4, sqlResult.length() - 1);
			}
			sqlResult.append("order by ");
			if (pageable == null||pageable.getSort()==null) {
				sqlResult.append(" id desc");
			} else {
				int idx = 0;
				Sort sort=pageable.getSort();
				for (Sort.Order order : sort) {
					idx++;
					sqlResult.append(SearchFilterParser.camelToUnderline(order.getProperty())	+ "  "
							+ (order.getDirection().name()));
					sqlResult.append(",");
				}
				if (idx != 0) {
					sqlResult.deleteCharAt(sqlResult.length() - 1);
				}
			}

			BoundSql boundSql = new BoundSql(ms.getConfiguration(), sqlResult.toString(), null, null);
			return boundSql;
		}
	}
	class FindSqlSource implements SqlSource {
		private MappedStatement ms;
		private Class<?> entityClass;
		private String sql;
		private Map<String, Class<?>> fieldsTypes;
		private boolean unique;
		
		public FindSqlSource(MappedStatement ms, Class<?> entityClass, String sql, Map<String, Class<?>> fieldsTypes,
                             boolean unique) {
			this.ms = ms;
			this.entityClass = entityClass;
			this.sql = sql;
			this.fieldsTypes = fieldsTypes;
			this.unique = unique;
		}
		
		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			Assert.isInstanceOf(MapperMethod.ParamMap.class, parameterObject);
			MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
			String property = (String) paramMap.get("param1");
			SearchFilter searchFilter = SearchFilter.parse(property, paramMap.get("param2"));
			StringBuilder sqlResult = new StringBuilder();
			sqlResult.append(sql);
			sqlResult.append(" WHERE ");
			Class proType = fieldsTypes.get(searchFilter.fieldName);
			Assert.notNull(proType, "属性[" + searchFilter.fieldName + "]不存在");
			sqlResult.append(SearchFilterParser.parseSqlField(searchFilter, proType));
			if (unique) {
				sqlResult.append(" limit 1");
			}
			BoundSql boundSql = new BoundSql(ms.getConfiguration(), sqlResult.toString(), null, null);
			return boundSql;
		}
	}
}
