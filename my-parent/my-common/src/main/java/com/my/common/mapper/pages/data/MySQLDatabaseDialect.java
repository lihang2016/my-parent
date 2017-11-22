package com.my.common.mapper.pages.data;

import org.springframework.data.domain.Pageable;

public class MySQLDatabaseDialect implements DatabaseDialect {
	
	@Override
	public String pageSql(@SuppressWarnings("rawtypes") Pageable pageInfo, String originalSql) {

		int offset = pageInfo.getPageSize() * pageInfo.getPageNumber();
		if (offset < 0) {
			offset = 0;
		}
		String pageSql = originalSql + " limit " + offset + "," + pageInfo.getPageSize();
		return pageSql;
	}
	
	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.mysql;
	}
	
}
