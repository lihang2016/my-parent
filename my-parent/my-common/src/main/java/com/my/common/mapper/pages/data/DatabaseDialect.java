package com.my.common.mapper.pages.data;

import org.springframework.data.domain.Pageable;

public interface DatabaseDialect {
	DatabaseType getDatabaseType();
	
	String pageSql(@SuppressWarnings("rawtypes") Pageable pageInfo, String originalSql);
	
}
