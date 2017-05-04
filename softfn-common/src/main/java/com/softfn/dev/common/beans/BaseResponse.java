package com.softfn.dev.common.beans;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.softfn.dev.common.constants.ResponseCode;
import com.softfn.dev.common.interfaces.StatusCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p/>
 * BaseResponse 请求响应
 * <p/>
 *
 * @author softfn
 */
@JSONType(orders = {"code","msg","data"})
public final class BaseResponse<T> implements Serializable {
    private static final ResponseCode SUCCESS = ResponseCode.SUCCESS;
    /**
     * 状态码
     */
    private StatusCode code;
    /**
     * 消息(错误）
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    /**
     * JSONP回调函数名
     */
    @JSONField(serialize = false)
    private String callback;

    public BaseResponse() {
        this.setCode(SUCCESS);
        this.msg = SUCCESS.msg();
    }

    public BaseResponse(T data) {
        this();
        this.data = data;
    }

    public BaseResponse(T data, String callback) {
        this(data);
        this.callback = callback;
    }

    public StatusCode getCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    /**
     * JSONP跨域数据格式输出
     * @param callbackFunction JSONP回调函数名
     * @return callbackFunction(BaseResponse)
     */
    @Deprecated
    public final JSONPObject toJSONP(String callbackFunction) {
        JSONPObject jsonpObject = new JSONPObject(callbackFunction);
        jsonpObject.addParameter(this);
        return jsonpObject;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("msg", msg)
                .append("data", data)
                .append("callback", callback)
                .toString();
    }
}
