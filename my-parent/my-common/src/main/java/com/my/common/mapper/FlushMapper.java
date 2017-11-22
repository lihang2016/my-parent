
package com.my.common.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public interface FlushMapper<T> {
	@DeleteProvider(type = FlushProvider.class, method = "dynamicSQL")
	void flush();
	
	class FlushProvider extends MapperTemplate {
		
		public FlushProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
			super(mapperClass, mapperHelper);
		}
		
		public String flush(MappedStatement ms) {
			return "SELECT now();";
		}
	}
}
