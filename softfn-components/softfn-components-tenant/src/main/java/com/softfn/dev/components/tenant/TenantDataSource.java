package com.softfn.dev.components.tenant;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;

/**
 * <p/>
 * TenantDataSource 多租户数据源FOR tomcatjdbc
 * <p/>
 * <p/>
 *
 * @author softfn
 */
public class TenantDataSource extends DataSource {

    public TenantDataSource() {
        super(new TenantPoolProperties());
    }

    public TenantDataSource(PoolConfiguration poolProperties) {
        super(poolProperties);
    }

}
