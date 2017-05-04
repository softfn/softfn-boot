package com.softfn.dev.common.beans;

import com.softfn.dev.common.interfaces.Tenant;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * TenantInfo 租户信息
 * <p/>
 *
 * @author softfn
 */
public class TenantInfo implements Tenant {
    private String tenantId;
    private String schema;

    public TenantInfo() {
    }

    public TenantInfo(String tenantId, String schema) {
        this.tenantId = tenantId;
        this.schema = schema;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tenantId", tenantId)
                .append("schema", schema)
                .toString();
    }
}
