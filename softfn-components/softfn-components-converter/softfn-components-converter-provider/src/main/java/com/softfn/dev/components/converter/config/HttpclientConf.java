package com.softfn.dev.components.converter.config;

/**
 * <p/>
 * HttpclientConf
 * <p/>
 *
 * @author softfn
 */
public class HttpclientConf {
    private int connTotal = 0;
    private int connPerRoute = 0;

    public int getConnTotal() {
        return connTotal;
    }

    public void setConnTotal(int connTotal) {
        this.connTotal = connTotal;
    }

    public int getConnPerRoute() {
        return connPerRoute;
    }

    public void setConnPerRoute(int connPerRoute) {
        this.connPerRoute = connPerRoute;
    }
}
