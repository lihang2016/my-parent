
package com.my.biz.common.udc;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 *
 */
public class UDCUserType implements UserType, DynamicParameterizedType {

    private static final int[] SQL_TYPES = {Types.INTEGER};
    private String typeCode = null;

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return UDC.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y || ( x != null && y != null && x.equals( y ) );
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException,
            SQLException {
        int value = rs.getInt(names[0]);
        if (value == UDC.NULL_ITEM_VALUE) {
            return null;
        } else {
            return new UDC(typeCode, value);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException,
            SQLException {
        if (value == null) {
            st.setLong(index, UDC.NULL_ITEM_VALUE);
            return;
        }

        if (!(value instanceof UDC)) {
            throw new HibernateException("value is not type of " + UDC.class);
        }
        UDC udc = (UDC) value;
        if (!udc.getType().getCode().equals(typeCode)) {
            throw new IllegalArgumentException("typeCode不匹配:期望=" + typeCode + ",实际=" + udc.getType().getCode());
        }
        st.setInt(index, udc.getItemValue());
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object cached) throws HibernateException {
        return (Serializable) cached;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader != null) {
            //如果有枚举指定，则用枚举指定的值
            UDC.EnumTypeCode code = getAnnotation(reader.getAnnotationsMethod(), UDC.EnumTypeCode.class);
            if (code != null) {
                typeCode = code.value();
            } else {
                //如果有Column，则用Column指定的值
                Column column = getAnnotation(reader.getAnnotationsMethod(), Column.class);
                if (column != null) {
                    typeCode = column.name();
                } else {
                    //使用参数名称
                    typeCode = (String) parameters.get(PROPERTY);
                }
            }
        }
    }

    private <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> anClass) {
        for (Annotation annotation : annotations) {
            if (anClass.isInstance(annotation)) {
                return (T) annotation;
            }
        }
        return null;
    }
}
