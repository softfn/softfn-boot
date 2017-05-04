package com.softfn.dev.components.tenant;

import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.util.HashMap;

/**
 * <p/>
 * TenantPoolProperties 多租户连接池配置
 * <p/>
 *
 * @author softfn
 */
public class TenantPoolProperties extends PoolProperties {
    public static final String TENANT_SCHEMA_KEY = "$TENANT_SCHEMA$";
    private static HashMap<String, String> schemas = new HashMap<String, String>();

    /*@Override
    public String getUrl() {
        String url = super.getUrl();
        int index = url.indexOf(TENANT_SCHEMA_KEY);
        if (index > -1) {
            String schema = ThreadContext.get(ThreadContext.TENANT_SCHEMA_KEY);
            if (schema == null || schema.trim().length() < 1) {
                throw new TenantDataSourceException(ExceptionCode.EXCEPTION, "租户变量schema未注入ThreadLocal");
            } else {
                String sUrl = schemas.get(schema);
                if (sUrl == null) {
                    url = url.substring(0, index) + schema + url.substring(index + TENANT_SCHEMA_KEY.length());
                    schemas.put(schema, url);
                } else {
                    url = sUrl;
                }
            }
        }
        return url;
    }*/

    public void injectionTenant(String schema) {
        String url = this.getUrl();
        int index = url.indexOf(TENANT_SCHEMA_KEY);
        if (index > -1) {
            url = url.substring(0, index) + schema + url.substring(index + TENANT_SCHEMA_KEY.length());
            this.setUrl(url);
        }
    }
}
