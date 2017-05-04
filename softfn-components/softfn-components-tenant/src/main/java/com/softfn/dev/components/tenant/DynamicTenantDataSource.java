package com.softfn.dev.components.tenant;

import com.softfn.dev.common.exception.ExceptionCode;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * DynamicTenantDataSource 动态数据源支持多租户 mycat schema动态切换
 * <p/>
 *
 * @author softfn
 */
public class DynamicTenantDataSource extends AbstractDataSource implements InitializingBean {
    private Map<String, TenantDataSource> targetDataSources;

    private TenantDataSource defaultTenantDataSource;

    private Map<String, DataSource> resolvedDataSources;

    public void setTargetDataSources(Map<String, TenantDataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTenantDataSource(TenantDataSource defaultTenantDataSource) {
        this.defaultTenantDataSource = defaultTenantDataSource;
    }

    public void afterPropertiesSet() {
        if (this.defaultTenantDataSource == null) {
            throw new TenantDataSourceException(ExceptionCode.EXCEPTION, "defaultTargetDataSource参数未定义");
        }
        this.targetDataSources = new HashMap<String, TenantDataSource>();
        this.resolvedDataSources = new HashMap<String, DataSource>();
        for (Map.Entry<String, TenantDataSource> entry : this.targetDataSources.entrySet()) {
            String lookupKey = resolveSpecifiedLookupKey(entry.getKey());
            DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
            this.resolvedDataSources.put(lookupKey, dataSource);
        }
    }

    protected String resolveSpecifiedLookupKey(String lookupKey) {
        return lookupKey;
    }

    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource) dataSource;
        } else {
            throw new TenantDataSourceException(ExceptionCode.EXCEPTION, "无效数据源" + dataSource);
        }
    }


    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "多数据源未初始化");
        String lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
        if (dataSource == null) {
            PoolConfiguration poolProperties = defaultTenantDataSource.getPoolProperties();
            TenantPoolProperties tenantPoolProperties = new TenantPoolProperties();
            BeanUtils.copyProperties(poolProperties, tenantPoolProperties);
            tenantPoolProperties.injectionTenant(lookupKey);
            dataSource = new TenantDataSource(tenantPoolProperties);
            this.resolvedDataSources.put(lookupKey, dataSource);
        }
        if (dataSource == null) {
            throw new TenantDataSourceException(ExceptionCode.INVALID_PARAM, "未找到租户数据数据源 [" + lookupKey + "]");
        }
        return dataSource;
    }

    protected String determineCurrentLookupKey() {
        String schema = TenantHelper.get();
        if (schema == null || schema.trim().length() < 1) {
            throw new TenantDataSourceException(ExceptionCode.EXCEPTION, "租户变量schema未注入ThreadLocal");
        }
        return schema;
    }


}
