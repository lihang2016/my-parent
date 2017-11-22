package com.my.common.mapper.pages.data;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public final class DatabaseDialectManager {
	
	private static Map<DatabaseType, DatabaseDialect> dialects = Maps.newHashMap();
	static {
		register(new MySQLDatabaseDialect());
		register(new OracleDatabaseDialect());
	}
	
	private DatabaseDialectManager() {
	}

	public static String pageSql(Connection connection, Pageable pageInfo, String sql) {
		DatabaseType dbType = getDatabaseType(connection);
		return dialects.get(dbType).pageSql(pageInfo, sql);
	}
	
	public static DatabaseType getDatabaseType(Connection connection) {
		String jdbcUrl = getJdbcUrlFromDataSource(connection);
		if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			return DatabaseType.mysql;
		} else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
			return DatabaseType.oracle;
		} else {
			throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
		}
	}
	
	public static String getJdbcUrlFromDataSource(Connection connection) {
		try {
			if (connection == null) {
				throw new IllegalStateException("Connection was null");
			}
			return connection.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	private static void register(DatabaseDialect dialect) {
		dialects.put(dialect.getDatabaseType(), dialect);
	}
	
}
