package com.softfn.dev.common.util.lang;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * <p/>
 * UrlUtils
 * <p/>
 *
 * @author softfn
 */
public abstract class UrlUtils {

    public static String getLookupPathForRequest(HttpServletRequest request) {
        UrlPathHelper pathHelper = new UrlPathHelper();
        String pathForRequest = pathHelper.getLookupPathForRequest(request);
        String queryString = pathHelper.getOriginatingQueryString(request);
        if (StringUtils.hasText(queryString))
            return pathForRequest + "?" + queryString;
        else
            return pathForRequest;
    }
}
