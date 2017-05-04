package com.softfn.dev.components.tenant;

import com.softfn.dev.common.util.lang.ReflectUtil;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * <p/>
 * TenantInterceptor
 * <p/>
 *
 * @author softfn
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})
public class TenantInterceptor implements Interceptor {
    public static final Logger logger = LoggerFactory.getLogger(TenantInterceptor.class);
    private Properties properties;

    public Object intercept(Invocation invocation) throws Throwable {
        // 是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        // 通过反射获取到当前RoutingStatementHandler对象的delegate属性
        StatementHandler delegate = (StatementHandler) ReflectUtil.getValueByFieldName(handler, "delegate");
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getValueByFieldName(delegate, "mappedStatement");
        BoundSql boundSql = delegate.getBoundSql();

        // 注入Mycat租户schema
        injectMycatSchema(boundSql);

        Object returnValue = null;
        StopWatch clock = new StopWatch();
        clock.start();
        returnValue = invocation.proceed();
        clock.stop();
        long totalTimeMillis = clock.getTotalTimeMillis();
        printSql(getSql(mappedStatement.getConfiguration(), boundSql, totalTimeMillis), totalTimeMillis);

        return returnValue;
    }

    private void injectMycatSchema(BoundSql boundSql) throws NoSuchFieldException, IllegalAccessException {
        String mycatSql = "/*!mycat:schema=" + TenantHelper.get() + "*/ " + boundSql.getSql();
        ReflectUtil.setValueByFieldName(boundSql, "sql", mycatSql);
    }

    private void printSql(String sql, long cost) {
        if (properties.getProperty("showSql", "false").equals("true")) {
            if (cost > 1000) {
                logger.warn(sql);
            } else {
                logger.debug(sql);
            }
        }
    }

    public static String getSql(Configuration configuration, BoundSql boundSql, long time) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append("==>执行NativeSQL：");
        str.append(sql);
        str.append(" --耗时： ");
        str.append(time);
        str.append(" ms");
        return str.toString();
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = replaceFirst(sql, "?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = replaceFirst(sql, "?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = replaceFirst(sql, "?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 字符串替换，左边第一个。
     * 如果参数是变量，而且可能包含/或$，建议不要使用replaceAll和replaceFirst方法
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    private static String replaceFirst(String str, String oldStr, String newStr) {
        int i = str.indexOf(oldStr);
        if (i == -1) return str;
        str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
        return str;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
