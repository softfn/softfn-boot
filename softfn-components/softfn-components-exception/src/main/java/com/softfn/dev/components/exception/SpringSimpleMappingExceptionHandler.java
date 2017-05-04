package com.softfn.dev.components.exception;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.TimeoutException;
import com.alibaba.dubbo.rpc.InvokeException;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.Status;
import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.beans.StatusInfo;
import com.softfn.dev.common.exception.BaseException;
import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.common.exception.LogLevel;
import com.softfn.dev.common.interfaces.StatusCode;
import com.softfn.dev.common.util.lang.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p/>
 * SpringSimpleMappingExceptionHandler
 * <p/>
 * 1 定义
 * 2 消息（1字头）
 * ▪ 100 Continue
 * ▪ 101 Switching Protocols
 * ▪ 102 Processing
 * 3 成功（2字头）
 * ▪ 200 OK
 * ▪ 201 Created
 * ▪ 202 Accepted
 * ▪ 203 Non-Authoritative Information
 * ▪ 204 No Content
 * ▪ 205 Reset Content
 * ▪ 206 Partial Content
 * ▪ 207 Multi-Status
 * 4 重定向（3字头）
 * ▪ 300 Multiple Choices
 * ▪ 301 Moved Permanently
 * ▪ 302 Move temporarily
 * ▪ 303 See Other
 * ▪ 304 Not Modified
 * ▪ 305 Use Proxy
 * ▪ 306 Switch Proxy
 * ▪ 307 Temporary Redirect
 * 5 请求错误（4字头）
 * ▪ 400 Bad Request
 * ▪ 401 Unauthorized
 * ▪ 402 Payment Required
 * ▪ 403 Forbidden
 * ▪ 404 Not Found
 * ▪ 405 Method Not Allowed
 * ▪ 406 Not Acceptable
 * ▪ 407 Proxy Authentication Required
 * ▪ 408 Request Timeout
 * ▪ 409 Conflict
 * ▪ 410 Gone
 * ▪ 411 Length Required
 * ▪ 412 Precondition Failed
 * ▪ 413 Request Entity Too Large
 * ▪ 414 Request-URI Too Long
 * ▪ 415 Unsupported Media Type
 * ▪ 416 Requested Range Not Satisfiable
 * ▪ 417 Expectation Failed
 * ▪ 421There are too many connections from your internet address
 * ▪ 422 Unprocessable Entity
 * ▪ 423 Locked
 * ▪ 424 Failed Dependency
 * ▪ 425 Unordered Collection
 * ▪ 426 Upgrade Required
 * ▪ 449 Retry With
 * 6 服务器错误（5字头）
 * ▪ 500 Internal Server Error
 * ▪ 501 Not Implemented
 * ▪ 502 Bad Gateway
 * ▪ 503 Service Unavailable
 * ▪ 504 Gateway Timeout
 * ▪ 505 HTTP Version Not Supported
 * ▪ 506 Variant Also Negotiates
 * ▪ 507 Insufficient Storage
 * ▪ 509 Bandwidth Limit Exceeded
 * ▪ 510 Not Extended
 * ▪ 600 Unparseable Response Headers
 *
 * @author softfn
 */
public class SpringSimpleMappingExceptionHandler extends SimpleMappingExceptionResolver {
    public static final Logger logger = LoggerFactory.getLogger(SpringSimpleMappingExceptionHandler.class);
    /**
     * 请求超时状态码
     */
    public static final int REQUEST_TIMEOUT = 408;

    @Override
    protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        logHandle(handler, ex);

        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            if (isAsynchRequest(request) || isBaseResponse(handler)) { // JSON格式返回
                BaseResponse result = handleBaseResponse(ex);

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    writer.append(FastJsonUtil.serialize(result));
                    writer.flush();
                } catch (IOException e) {
                    logger.error("json serialize error", e);
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
                return null;
            } else { // JSP格式返回
                Integer statusCode;
                if (isTimeoutException(ex)) {
                    statusCode = REQUEST_TIMEOUT;
                } else {
                    statusCode = determineStatusCode(request, viewName);
                }
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                if (isTimeoutException(ex)) {
                    return getModelAndView(viewName, (Exception) ex.getCause(), request);
                }
                return getModelAndView(viewName, ex, request);
            }
        } else {
            return null;
        }
    }

    private boolean isBaseResponse(Object handler) {
        if (handler instanceof HandlerMethod) {
            Class<?> claz = ((HandlerMethod) handler).getMethod().getReturnType();
            if (claz.equals(BaseResponse.class))
                return true;
        }
        return false;
    }

    private BaseResponse handleBaseResponse(Exception ex) {
        BaseResponse result = new BaseResponse();
        StatusCode code;

        if (ex instanceof RpcException) {
            code = adaptCode(((RpcException) ex).getCode());
        } else if (ex instanceof InvokeException) {
            InvokeException exception = (InvokeException) ex;
            Status status = exception.getStatus();
            String message = exception.getMessage();
            if (!StringUtils.hasText(message) || message.length() > 200)
                message = status.msg();
            code = new StatusInfo(status.code(), message);
        } else if (ex instanceof BaseException) {
            code = ((BaseException) ex).getStatus();
        } else if (ex instanceof RemotingException) {
            code = ExceptionCode.REMOTING_EXCEPTION;
        } else {
            String message = ex.getMessage();
            if (StringUtils.hasText(message) && message.length() < 200) // message过长不输出 针对不是用户自定义的异常信息 不直接输出给用户
                code = new StatusInfo(ExceptionCode.EXCEPTION.code(), message);
            else
                code = ExceptionCode.UNKNOWN_EXCEPTION;
        }
        result.setCode(code);
        result.setMsg(code.msg());

        return result;
    }

    @Override
    protected String determineViewName(Exception ex, HttpServletRequest request) {
        if (isTimeoutException(ex)) {
            return super.determineViewName((TimeoutException) ex.getCause(), request);
        }
        return super.determineViewName(ex, request);
    }

    private String determineViewName(HttpServletRequest request, Exception ex) {
        String viewName;
        if (isTimeoutException(ex)) {
            viewName = determineViewName((TimeoutException) ex.getCause(), request);
        } else {
            viewName = determineViewName(ex, request);
        }
        return viewName;
    }

    private boolean isTimeoutException(Exception ex) {
        return ex instanceof TimeoutException
                || (ex instanceof RpcException && ex.getCause() instanceof TimeoutException);
    }

    /**
     * dubbo状态码转换 见 RpcException
     *
     * @param code
     * @return
     */
    private static StatusCode adaptCode(int code) {
        if (code == 0) {
            return ExceptionCode.UNKNOWN_EXCEPTION;
        } else if (code == 1) {
            return ExceptionCode.NETWORK_EXCEPTION;
        } else if (code == 2) {
            return ExceptionCode.TIMEOUT_EXCEPTION;
        } else if (code == 3) {
            return ExceptionCode.BIZ_EXCEPTION;
        } else if (code == 4) {
            return ExceptionCode.FORBIDDEN_EXCEPTION;
        } else if (code == 5) {
            return ExceptionCode.SERIALIZATION_EXCEPTION;
        } else {
            return ExceptionCode.EXCEPTION;
        }
    }

    private boolean isAsynchRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") > -1)
            return true;
        String header = request.getHeader("X-Requested-With");
        if (header != null && header.indexOf("XMLHttpRequest") > -1)
            return true;
        return false;
    }

    private void logHandle(Object handler, Exception ex) {
        String position = handler == null ? "" : handler.toString();
        if (ex instanceof BaseException) {
            LogLevel logLevel = ((BaseException) ex).getLogLevel();
            switch (logLevel) {
                case trace:
                    logger.trace(position, ex);
                    break;
                case debug:
                    logger.debug(position, ex);
                    break;
                case info:
                    logger.info(ex.getMessage());
                    break;
                case warn:
                    logger.warn(position, ex);
                    break;
                case error:
                    logger.debug(position, ex);
                    break;
                default:
                    logger.error(position, ex);
                    break;
            }
        } else {
            logger.error(position, ex);
        }
    }
}
