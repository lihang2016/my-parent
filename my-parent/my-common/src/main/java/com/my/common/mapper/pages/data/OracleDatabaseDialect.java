package com.my.common.mapper.pages.data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

public class OracleDatabaseDialect implements DatabaseDialect {
	
	private static final String ORACLE_PAGESQL_TEMPLATE = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX ) ZZ"
															+ " where ZZ.ROW_NUM BETWEEN ${startNum} AND ${endNum}";
	
	@Override
	public String pageSql(@SuppressWarnings("rawtypes") Pageable pageInfo, String originalSql) {
		String pageSql = ORACLE_PAGESQL_TEMPLATE;
		int startNum = pageInfo.getPageSize() * (pageInfo.getPageNumber()) + 1;
		int endNum = pageInfo.getPageSize() * (pageInfo.getPageNumber());
		pageSql = StringUtils.replace(pageSql, "${sql}", originalSql);
		pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
		pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
		
		return pageSql;
	}
	
	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.oracle;
	}
	
}
