package com.softfn.dev.components.log;

import com.softfn.dev.common.util.lang.FastJsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * <p>
 * AbstractPrintLog
 * </p>
 *
 * @author softfn
 */
abstract class AbstractPrintLog {
    public static final Logger logger = LoggerFactory.getLogger(AbstractPrintLog.class);
    private static final String MSG = "--请求--\n --方法：{}\n --描述：{}\n --位置：{}\n --参数：{}\n --返回：{}\n --耗时：{} ms";
    /**
     * 服务响应超过1秒打印警告日志
     */
    private static final int DEFAULT_TIME_LIMIT = 3000;

    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (logger.isDebugEnabled() || logger.isWarnEnabled()) {
            StopWatch clock = new StopWatch();
            clock.start();

            Object returnObj = null;
            try {
                return returnObj = joinPoint.proceed(args);
            } catch (Exception e) {
                throw e;
            } finally {
                clock.stop();
                long totalTimeMillis = clock.getTotalTimeMillis();

                handleLog(joinPoint, args, returnObj, totalTimeMillis);
            }
        } else {
            return joinPoint.proceed(args);
        }
    }

    /**
     * 日志处理
     *
     * @param joinPoint 位置
     * @param args      参数
     * @param returnObj 响应
     * @param costTime  耗时ms
     */
    protected abstract void handleLog(ProceedingJoinPoint joinPoint, Object[] args, Object returnObj, long costTime);

    /**
     * @param name            操作名称
     * @param description     描述
     * @param printReturn     是否打印响应
     * @param joinPoint       位置
     * @param args            参数
     * @param returnObj       响应
     * @param totalTimeMillis 耗时ms
     */
    protected void printLogMsg(String name, String description, boolean printReturn, JoinPoint joinPoint, Object[] args, Object returnObj, long totalTimeMillis) {
        Object[] params = argsDemote(args);
        if (totalTimeMillis < getTimeLimit())
            logger.debug(MSG, new Object[]{name, description, joinPoint.getStaticPart(), params, getPrintMsg(printReturn, returnObj), totalTimeMillis});
        else
            logger.warn(MSG, new Object[]{name, description, joinPoint.getStaticPart(), params, getPrintMsg(printReturn, returnObj), totalTimeMillis});
    }

    protected int getTimeLimit() {
        return DEFAULT_TIME_LIMIT;
    }

    private String getPrintMsg(boolean printReturn, Object returnObj) {
        return printReturn ? ((returnObj != null) ? FastJsonUtil.serialize(returnObj) : "null") : "[printReturn = false]";
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected Object[] argsDemote(Object[] args) {
        if (args == null || args.length == 0) {
            return new Object[]{};
        }
        Object[] params = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof ServletRequest || arg instanceof ServletResponse
                    || arg instanceof ModelMap || arg instanceof Model
                    || arg instanceof InputStreamSource || arg instanceof File) {
                params[i] = args.toString();
            } else {
                params[i] = args[i];
            }
        }
        return params;
    }
}
