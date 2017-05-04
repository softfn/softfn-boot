package com.softfn.dev.common.interfaces;

import java.io.Serializable;

/**
 * <p/>
 * Tenant 多租户支持接口
 * <p/>
 *
 * @author softfn
 */
public interface Tenant extends Serializable {
    /**
     * 获取租户id
     * @return
     */
    String getTenantId();
    /**
     * 租户拥有的schema (每个租户默认对应Mycat一个schema)
     * @return
     */
    String getSchema();
}
