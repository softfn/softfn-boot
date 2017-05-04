package com.softfn.dev.common.exception;

import com.softfn.dev.common.annotation.Statuscode;
import com.softfn.dev.common.interfaces.StatusCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * ExceptionCode 异常编码
 * <p/>
 *
 * @author softfn
 */
@Statuscode
public enum ExceptionCode implements StatusCode {
    EXCEPTION(3, "异常"),
    INVALID_PARAM(4, "参数错误"),

    UNKNOWN_EXCEPTION(50001, "未知异常"),
    NETWORK_EXCEPTION(50002, "网络异常"),
    TIMEOUT_EXCEPTION(50003, "请求超时"),
    BIZ_EXCEPTION(50004, "业务异常"),
    FORBIDDEN_EXCEPTION(50005, "请求禁止"),
    SERIALIZATION_EXCEPTION(50006, "序列化异常"),
    REMOTING_EXCEPTION(50007, "远程通信异常"),

    UPLOAD_FILE_EXCEPTION(60001, "文件上传失败"),
    DELETE_FILE_EXCEPTION(60002, "文件删除失败"),
    DOWNLOAD_FILE_EXCEPTION(60003, "文件下载失败"),
    TOKEN_EXPIRED_OR_INVALID(60004, "访问令牌过期或无效");

    private int code;
    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("msg", msg)
                .toString();
    }
}
