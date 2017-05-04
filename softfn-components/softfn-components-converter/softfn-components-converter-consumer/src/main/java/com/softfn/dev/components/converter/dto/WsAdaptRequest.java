package com.softfn.dev.components.converter.dto;

import com.softfn.dev.common.beans.BaseRequest;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * <p/>
 * WsAdaptRequest
 * <p/>
 *
 * @author softfn
 */
public class WsAdaptRequest extends BaseRequest {
    @NotBlank(message = "uri is null or blank")
    @URL(message = "uri is illegal")
    private String uri;
    private String charset;

    public WsAdaptRequest() {
    }

    public WsAdaptRequest(String uri) {
        this.uri = uri;
    }

    public WsAdaptRequest(String uri, String charset) {
        this.uri = uri;
        this.charset = charset;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
