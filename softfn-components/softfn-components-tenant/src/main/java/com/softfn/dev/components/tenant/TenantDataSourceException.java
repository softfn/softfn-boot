package com.softfn.dev.components.tenant;

import com.softfn.dev.common.exception.BaseException;
import com.softfn.dev.common.interfaces.StatusCode;

/**
 * <p/>
 * TenantDataSourceException 租户异常
 * <p/>
 *
 * @author softfn
 */
public class TenantDataSourceException extends BaseException {
    public TenantDataSourceException() {
    }

    public TenantDataSourceException(StatusCode errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public TenantDataSourceException(StatusCode errcode, String errmsg, Throwable cause) {
        super(errcode, errmsg, cause);
    }
}
