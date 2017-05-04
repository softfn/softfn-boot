package com.softfn.dev.common.exception;

import com.alibaba.dubbo.rpc.InvokeException;
import com.softfn.dev.common.interfaces.StatusCode;

/**
 * <p/>
 * BaseException 异常基类，所有业务异常都必须继承于此异常
 * <p/>
 *
 * @author softfn
 */
public class BaseException extends InvokeException {
    private LogLevel logLevel = LogLevel.error;

    public BaseException() {
    }

    public BaseException(StatusCode statusCode) {
        super(statusCode);
    }

    public BaseException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }

    public BaseException(StatusCode statusCode, Throwable cause) {
        super(statusCode, cause);
    }

    public BaseException(StatusCode statusCode, String message, Throwable cause) {
        super(statusCode, message, cause);
    }

    @Override
    public StatusCode getStatus() {
        return (StatusCode) super.getStatus();
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
