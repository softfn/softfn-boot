package com.softfn.dev.common.beans;

import com.softfn.dev.common.interfaces.StatusCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * StatusInfo
 * <p/>
 *
 * @author softfn
 */
public class StatusInfo implements StatusCode {
    private int code;
    private String msg;

    public StatusInfo() {
    }

    public StatusInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
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
