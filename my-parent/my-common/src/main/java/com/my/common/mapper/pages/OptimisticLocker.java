package com.my.common.mapper.pages;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Intercepts({	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }),
				@Signature(type = ParameterHandler.class, method = "setParameters",
						args = { PreparedStatement.class }) })
public class OptimisticLocker implements Interceptor {
	
	private static final Log log = LogFactory.getLog(OptimisticLocker.class);
	public static final String VERSION = "version";
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object intercept(Invocation invocation) throws Exception {
		
		String versionColumn = VERSION;
		String interceptMethod = invocation.getMethod().getName();
		if ("prepare".equals(interceptMethod)) {
			
			StatementHandler handler = (StatementHandler) processTarget(invocation.getTarget());
			MetaObject hm = SystemMetaObject.forObject(handler);
			
			MappedStatement ms = (MappedStatement) hm.getValue("delegate.mappedStatement");
			SqlCommandType sqlCmdType = ms.getSqlCommandType();
			if (sqlCmdType != SqlCommandType.UPDATE) {
				return invocation.proceed();
			}

			Object originalVersion = null;
			try {
				originalVersion = hm.getValue("delegate.boundSql.parameterObject." + versionColumn);
			} catch (BindingException e) {
				return invocation.proceed();
			}
			Object versionIncr = castTypeAndOptValue(originalVersion, hm.getValue("delegate.boundSql.parameterObject"),
				ValueType.INCREASE);
			hm.setValue("delegate.boundSql.parameterObject." + versionColumn, versionIncr);
			
			String originalSql = (String) hm.getValue("delegate.boundSql.sql");
			//设置version+1
			int index=originalSql.indexOf("where")==-1?originalSql.indexOf("WHERE"):originalSql.indexOf("where");
			String newSql=originalSql.substring(0,index)+",version=version+1 "+originalSql.substring(index,originalSql.length());
			StringBuilder builder = new StringBuilder(newSql);
			//追加乐观锁版本控制语句
			builder.append(" and ");
			builder.append(versionColumn);
			builder.append(" = ?");
			hm.setValue("delegate.boundSql.sql", builder.toString());
			
			if (log.isDebugEnabled()) {
				log.debug("==> originalSql: " + originalSql);
			}
			
			return invocation.proceed();
			
		} else if ("setParameters".equals(interceptMethod)) {
			//设置乐观锁版本的值
			ParameterHandler handler = (ParameterHandler) processTarget(invocation.getTarget());
			MetaObject hm = SystemMetaObject.forObject(handler);
			
			MappedStatement ms = (MappedStatement) hm.getValue("mappedStatement");
			SqlCommandType sqlCmdType = ms.getSqlCommandType();
			if (sqlCmdType != SqlCommandType.UPDATE) {
				return invocation.proceed();
			}
			Configuration configuration = ms.getConfiguration();
			BoundSql boundSql = (BoundSql) hm.getValue("boundSql");
			

			Object result = invocation.proceed();
			
			ParameterMapping versionMapping = new ParameterMapping.Builder(configuration, versionColumn, Object.class)
				.build();
			
			Object parameterObject = boundSql.getParameterObject();
			
			MetaObject pm = configuration.newMetaObject(parameterObject);
			if (parameterObject instanceof MapperMethod.ParamMap<?>) {
				MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap<?>) parameterObject;
				if (!paramMap.containsKey(versionColumn)) {
					return invocation.proceed();
				}
			}
			Object value = pm.getValue(versionColumn);
			TypeHandler typeHandler = versionMapping.getTypeHandler();
			JdbcType jdbcType = versionMapping.getJdbcType();
			
			if (value == null && jdbcType == null) {
				jdbcType = configuration.getJdbcTypeForNull();
			}
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			try {
				PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
				Object val = castTypeAndOptValue(value, parameterObject, ValueType.DECREASE);
				typeHandler.setParameter(ps, parameterMappings.size() + 1, val, jdbcType);
			} catch (TypeException | SQLException e) {
				throw new TypeException("Could not set parameters for mapping: " + parameterMappings + ". Cause: " + e,
					e);
			}
			return result;
		}
		return invocation.proceed();
	}
	
	private Object castTypeAndOptValue(Object value, Object parameterObject, ValueType vt) {
		Class<?> valType = value.getClass();
		if (valType == Long.class || valType == long.class) {
			return (Long) value + vt.value;
		} else if (valType == Integer.class || valType == int.class) {
			return (Integer) value + vt.value;
		} else if (valType == Float.class || valType == float.class) {
			return (Float) value + vt.value;
		} else if (valType == Double.class || valType == double.class) {
			return (Double) value + vt.value;
		} else if (valType == Short.class || valType == short.class) {
			return (short) ((short) value + vt.value);
		} else {
			if (parameterObject instanceof MapperMethod.ParamMap<?>) {
				throw new TypeException("All the base type parameters must add MyBatis's @Param Annotaion");
			} else {
				throw new TypeException("Property 'version' in "	+ parameterObject.getClass().getSimpleName()
										+ " must be [ long, int, float, double ] or [ Long, Integer, Float, Double ]");
			}
		}
	}
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler || target instanceof ParameterHandler)
			return Plugin.wrap(target, this);
		return target;
	}
	
	@Override
	public void setProperties(Properties properties) {
	}
	public static Object processTarget(Object target) {
		if(Proxy.isProxyClass(target.getClass())) {
			MetaObject mo = SystemMetaObject.forObject(target);
			return processTarget(mo.getValue("h.target"));
		}
		return target;
	}
	private enum ValueType {
							INCREASE(1),
							DECREASE(-1);
		
		private Integer value;
		
		ValueType(Integer value) {
			this.value = value;
		}
	}
	
}