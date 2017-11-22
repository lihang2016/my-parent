
package com.my.common.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

import java.io.Serializable;
import java.util.List;

public interface GetMapper<T> {
	@SelectProvider(type = GetProvider.class, method = "dynamicSQL")
	T get(Serializable id);
	
	@SelectProvider(type = GetProvider.class, method = "dynamicSQL")
	List<T> getAll();
	
	class GetProvider extends BaseSelectProvider {
		
		public GetProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
			super(mapperClass, mapperHelper);
		}
		
		public String get(MappedStatement ms) {
			return super.selectByPrimaryKey(ms);
		}
		
		public String getAll(MappedStatement ms) {
			return super.selectAll(ms);
		}
	}
	
}
