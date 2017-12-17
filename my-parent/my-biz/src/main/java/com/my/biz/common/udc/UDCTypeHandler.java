package com.my.biz.common.udc;

import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(UDC.class)
@MappedJdbcTypes({ JdbcType.BIGINT, JdbcType.DECIMAL, JdbcType.NUMERIC })
public class UDCTypeHandler extends BaseTypeHandler<UDC> {
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UDC udc, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, udc.getItemValue());
	}
    @Override
    public void setParameter(PreparedStatement ps, int i, UDC udc, JdbcType jdbcType) throws SQLException {
        if (udc == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                ps.setInt(i,UDC.NULL_ITEM_VALUE);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        } else {
            try {
                ps.setInt(i, udc.getItemValue());
            } catch (Exception e) {
                throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different configuration property. " +
                        "Cause: " + e, e);
            }
        }
    }
	@Override
	public UDC getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int result = rs.getInt(columnName);
        if (result == UDC.NULL_ITEM_VALUE) {
            return null;
        } else {
            return  UDC.newUDCWithItemValue(columnName, result);
        }
	}
	
	@Override
	public UDC getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		throw new UnsupportedOperationException("");
	}
	
	@Override
	public UDC getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("");
    }
	
}
