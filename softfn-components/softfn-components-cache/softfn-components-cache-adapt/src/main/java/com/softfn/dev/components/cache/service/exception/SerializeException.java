package com.softfn.dev.components.cache.service.exception;

import com.softfn.dev.common.interfaces.StatusCode;
import com.softfn.dev.common.exception.BaseException;

/**
 * <p/>
 * SerializeException 对象序列化异常
 * <p/>
 *
 * @author softfn
 */
public class SerializeException extends BaseException {
    public SerializeException() {
    }

    public SerializeException(StatusCode errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public SerializeException(StatusCode errcode, String errmsg, Throwable cause) {
        super(errcode, errmsg, cause);
    }
}
