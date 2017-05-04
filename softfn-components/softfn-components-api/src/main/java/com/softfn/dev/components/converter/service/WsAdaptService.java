package com.softfn.dev.components.converter.service;

/**
 * <p/>
 * WsAdaptService ESB/WS适配器服务
 * <p/>
 * 将WS返回SOAP协议的XML转成JSON字符串
 *
 * @author softfn
 */
public interface WsAdaptService {
    String xmlToJson(String xml, String charset);

    String getJsonDataFromWebservice(String uri, String charset);
}
