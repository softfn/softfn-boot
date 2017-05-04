package com.softfn.dev.components.converter.service.impl;

import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.components.converter.config.HttpclientConf;
import com.softfn.dev.components.converter.exception.ConverterException;
import com.softfn.dev.components.converter.service.WsAdaptService;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * <p/>
 * WsAdaptServiceImpl  WS适配器服务实现
 * <p/>
 *
 * @author softfn
 */
@Service
public class WsAdaptServiceImpl implements WsAdaptService, InitializingBean, DisposableBean {
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(WsAdaptServiceImpl.class);
    private CloseableHttpClient httpclient;
    @Autowired
    private HttpclientConf httpclientConf;

    public String xmlToJson(String xml) throws ConverterException {
        return this.xmlToJson(xml, DEFAULT_CHARSET);
    }

    public String xmlToJson(String xml, String charset) throws ConverterException {
        XMLSerializer xmlserializer = new XMLSerializer();
        String cx = new String(xml.getBytes(Charset.forName(charset)));
        return xmlserializer.read(cx).toString();
    }

    public String getJsonDataFromWebservice(String uri) throws ConverterException {
        return this.getJsonDataFromWebservice(uri, DEFAULT_CHARSET);
    }

    public String getJsonDataFromWebservice(final String uri, final String charset) throws ConverterException {
        HttpGet httpget = new HttpGet(uri);
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        try {
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, charset == null ? DEFAULT_CHARSET : charset) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            long startTime = System.currentTimeMillis();
            String resStr = httpclient.execute(httpget, responseHandler, context);
            logger.info("获取数据耗时：{}毫秒,URI:{}", System.currentTimeMillis() - startTime, uri);

            if (!StringUtils.isBlank(resStr)) {
                if (resStr.trim().startsWith("{")) { // 服务直接返回JSON字符串
                    return resStr;
                } else if (resStr.trim().startsWith("<")) { // 服务直接返回XML字符串
                    return xmlToJson(resStr);
                } else {
                    logger.error("未知返回数据类型，响应数据={}", resStr);
                }
            }
            return resStr;

        } catch (Exception e) {
            String errorMsg = "获取数据失败,URI:" + uri;
            logger.error(errorMsg, e);
            throw new ConverterException(ExceptionCode.EXCEPTION, errorMsg, e);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(httpclientConf);

        httpclient = HttpClients.custom()
                .setMaxConnTotal(httpclientConf.getConnTotal())
                .setMaxConnPerRoute(httpclientConf.getConnPerRoute())
                .build();
    }

    public void destroy() throws Exception {
        if (httpclient != null) {
            httpclient.close();
        }
    }
}
