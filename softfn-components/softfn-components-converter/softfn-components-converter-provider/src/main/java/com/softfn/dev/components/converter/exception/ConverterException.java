package com.softfn.dev.components.converter.exception;

import com.softfn.dev.common.interfaces.StatusCode;
import com.softfn.dev.common.exception.BaseException;

/**
 * <p/>
 * ConverterException 转换服务异常
 * <p/>
 *
 * @author softfn
 */
public class ConverterException extends BaseException {
    public ConverterException() {
        super();
    }

    public ConverterException(StatusCode errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public ConverterException(StatusCode errcode, String errmsg, Throwable cause) {
        super(errcode, errmsg, cause);
    }
}
