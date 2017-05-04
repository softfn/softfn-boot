package com.softfn.dev.common.util.spring;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * <p/>
 * MultipartResolver 文件上传控制器
 * <p/>
 *
 * @author softfn
 */
public class MultipartResolver extends CommonsMultipartResolver {
    private String excludeUrl;

    public String getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(final String excludeUrlParm) {
        this.excludeUrl = excludeUrlParm;
    }

    @Override
    public boolean isMultipart(final HttpServletRequest request) {
        if (request.getRequestURI().contains(excludeUrl)) {
            return false;
        }
        return super.isMultipart(request);
    }
}