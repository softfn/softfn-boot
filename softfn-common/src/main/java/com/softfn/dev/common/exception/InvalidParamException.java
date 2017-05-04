package com.softfn.dev.common.exception;

/**
 * <p/>
 * InvalidParamException 参数无效异常
 * <p/>
 *
 * @author softfn
 */
public class InvalidParamException extends BaseException {
    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String errmsg) {
        super(ExceptionCode.INVALID_PARAM, errmsg);
    }

    public InvalidParamException(String errmsg, Throwable cause) {
        super(ExceptionCode.INVALID_PARAM, errmsg, cause);
    }
}
