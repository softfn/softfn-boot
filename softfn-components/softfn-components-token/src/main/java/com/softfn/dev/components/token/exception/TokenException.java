package com.softfn.dev.components.token.exception;

import com.softfn.dev.common.exception.BaseException;
import com.softfn.dev.common.exception.LogLevel;
import com.softfn.dev.common.interfaces.StatusCode;

/**
 * <p/>
 * TokenException
 * <p/>
 *
 * @author softfn
 */
public class TokenException extends BaseException {
    public TokenException() {
    }

    public TokenException(StatusCode errcode) {
        super(errcode, errcode.msg());
    }

    public TokenException(StatusCode errcode, LogLevel logLevel) {
        super(errcode, errcode.msg());
        this.setLogLevel(logLevel);
    }

    public TokenException(StatusCode errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public TokenException(StatusCode errcode, String errmsg, Throwable cause) {
        super(errcode, errmsg, cause);
    }
}
