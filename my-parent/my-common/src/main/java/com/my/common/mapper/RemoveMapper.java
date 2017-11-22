
package com.my.common.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

import java.io.Serializable;

public interface RemoveMapper<T> {
	
	@DeleteProvider(type = RemoveProvider.class, method = "dynamicSQL")
	void remove(T o);
	
	@DeleteProvider(type = RemoveProvider.class, method = "dynamicSQL")
	void removeById(Serializable id);
	
	@DeleteProvider(type = RemoveProvider.class, method = "dynamicSQL")
	void removes(Serializable... ids);

	@DeleteProvider(type = RemoveProvider.class, method = "dynamicSQL")
	void removeAll();
	
	class RemoveProvider extends BaseDeleteProvider {
		
		public RemoveProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
			super(mapperClass, mapperHelper);
		}
		
		public String removeById(MappedStatement ms) {
			return super.deleteByPrimaryKey(ms);
		}
		
		public String remove(MappedStatement ms) {
			return super.delete(ms);
		}
		
		public String removes(MappedStatement ms) {
			Class<?> entityClass = getEntityClass(ms);
			StringBuilder sql = new StringBuilder();
			sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
			sql.append(" WHERE ID IN");
			sql.append(
				"<foreach item=\"item\" index=\"index\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">  \n"
						+ " #{item}  \n" + "</foreach>  ");
			return sql.toString();
		}
		public String removeAll(MappedStatement ms) {
			Class<?> entityClass = getEntityClass(ms);
			StringBuilder sql = new StringBuilder();
			sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
			return sql.toString();
		}
	}
}
