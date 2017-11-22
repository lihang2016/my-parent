package com.my.common.mapper.pages;

import com.my.common.mapper.pages.data.DatabaseDialectManager;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageableExecutorInterceptor implements Interceptor {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Pageable pageInfo = havePageInfoArg(invocation);
        // 开始处理分页拦截
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // 待参数的原始SQL
        String originalSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();

        if (pageInfo == null) {
            //自定义sql分页
            if(parameterObject instanceof MapperMethod.ParamMap){
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
                if(paramMap==null) return invocation.proceed();
                try {
                    ExPageImpl<?> exPage = (ExPageImpl<?>) paramMap.get("exPage");
                    if (exPage == null) {
                        return invocation.proceed();
                    }
                    // 总记录数
                    long totalCount = getCount(mappedStatement, originalSql, boundSql, parameterObject);
                    exPage.setTotal(totalCount);
                    String pageSql = getPageSql(mappedStatement, exPage.getPageable(), originalSql);
                    BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, pageSql);
                    MappedStatement newMapperStmt = copyFromMappedStatement(mappedStatement, newBoundSql);
                    invocation.getArgs()[0] = newMapperStmt;
                    return invocation.proceed();
                } catch (BindingException e) {
                    return invocation.proceed();
                }
            }else {
               return invocation.proceed();
            }
        } else {
            //动态拼装sql分页
            String pageSql = getPageSql(mappedStatement, pageInfo, originalSql);
            BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, pageSql);
            MappedStatement newMapperStmt = copyFromMappedStatement(mappedStatement, newBoundSql);
            invocation.getArgs()[0] = newMapperStmt;
            List result = (List) invocation.proceed();
            ExPageImpl page = null;
            // 总记录数
            long totalCount = getCount(mappedStatement, originalSql, boundSql, parameterObject);
            if (result == null) {

                page = new ExPageImpl(Collections.emptyList(), pageInfo, totalCount);
            } else {
                page = new ExPageImpl(result, pageInfo, totalCount);
            }
            PageList pageList = new PageList<>();
            pageList.setEs(page);
            return pageList;
        }
    }

    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {

    }

    @SuppressWarnings("rawtypes")
    private String getPageSql(MappedStatement mappedStatement, Pageable pageInfo, String originalSql) throws Throwable {
        Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
        return DatabaseDialectManager.pageSql(connection, pageInfo, originalSql);

    }

    private long getCount(MappedStatement mappedStatement, String originalSql, BoundSql boundSql,
                          Object parameterObject) throws Throwable {
        String countSql = getCountSql(originalSql);
        Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
        PreparedStatement countStmt = connection.prepareStatement(countSql);
        BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
                countBS);
        parameterHandler.setParameters(countStmt);
        ResultSet rs = countStmt.executeQuery();
        long totpage = 0;
        if (rs.next()) {
            totpage = rs.getLong(1);
        }
        rs.close();
        countStmt.close();
        connection.close();
        return totpage;
    }

    private String getCountSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ") forPageCount";
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, final BoundSql newBoundSql) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                new SqlSource() {
                    @Override
                    public BoundSql getBoundSql(Object parameterObject) {
                        return newBoundSql;
                    }

                }, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        // builder.keyProperty(ms.getKeyProperties())
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private Pageable havePageInfoArg(Invocation invocation) {
        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return null;
        }
        if (Map.class.isAssignableFrom(parameter.getClass())) {
            for (Object v : ((Map) parameter).values()) {
                if (v instanceof Pageable) {
                    return (Pageable) v;
                }
            }
        } else if (parameter instanceof Pageable) {
            return (Pageable) parameter;
        }
        return null;
    }

}
