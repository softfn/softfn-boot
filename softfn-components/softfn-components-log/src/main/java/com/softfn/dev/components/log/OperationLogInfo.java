package com.softfn.dev.components.log;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * <p>
 * OperationLogInfo 操作日志描述类
 * </p>
 *
 * @author softfn
 */
public class OperationLogInfo {
    /**
     * 资源权限
     */
    private String authority;
    /**
     * 请求UII
     */
    private String uri;
    /**
     * 操作方法位置
     */
    private String method;
    /**
     * 请求方式
     */
    private String action;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 方法响应
     */
    private String returnObj;
    /**
     * 客户端IP
     */
    private String ip;
    /**
     * 耗时ms
     */
    private int costTime;
    /**
     * 操作时间
     */
    private Date operateTime;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(String returnObj) {
        this.returnObj = returnObj;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authority", authority)
                .append("uri", uri)
                .append("method", method)
                .append("action", action)
                .append("params", params)
                .append("returnObj", returnObj)
                .append("ip", ip)
                .append("costTime", costTime)
                .append("operateTime", operateTime)
                .toString();
    }
}
