package com.softfn.dev.common.constants;

import com.softfn.dev.common.annotation.Statuscode;
import com.softfn.dev.common.interfaces.StatusCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * ResponseCode 响应码
 * <p/>
 *
 * @author softfn
 */
@Statuscode
public enum ResponseCode implements StatusCode {
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private int code;
    private String msg;

    ResponseCode(int value, String text) {
        this.code = value;
        this.msg = text;
    }

    public int code() {
        return this.code;
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
