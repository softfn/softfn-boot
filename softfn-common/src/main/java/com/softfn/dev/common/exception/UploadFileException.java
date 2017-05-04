package com.softfn.dev.common.exception;

import com.softfn.dev.common.interfaces.StatusCode;

/**
 * <p/>
 * UploadFileException 上传文件异常
 * <p/>
 *
 * @author softfn
 */
public class UploadFileException extends BaseException {
    public UploadFileException() {
    }

    public UploadFileException(StatusCode statusCode) {
        super(statusCode);
    }

    public UploadFileException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }

    public UploadFileException(StatusCode statusCode, Throwable cause) {
        super(statusCode, cause);
    }

    public UploadFileException(StatusCode statusCode, String message, Throwable cause) {
        super(statusCode, message, cause);
    }
}
