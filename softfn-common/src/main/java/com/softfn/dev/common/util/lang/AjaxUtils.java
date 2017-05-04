package com.softfn.dev.common.util.lang;

import javax.servlet.http.HttpServletRequest;

/**
 * <p/>
 * AjaxUtils
 * <p/>
 *
 * @author softfn
 */
public abstract class AjaxUtils {
    /**
     * 判断请求是否为ajax
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
}
