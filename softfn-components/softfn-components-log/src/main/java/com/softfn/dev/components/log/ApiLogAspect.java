package com.softfn.dev.components.log;

import com.softfn.dev.common.annotation.ApiLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <p/>
 * ApiLogAspect API日志AOP
 * <p/>
 *
 * @author softfn
 */
public class ApiLogAspect extends AbstractPrintLog {
    protected void handleLog(ProceedingJoinPoint joinPoint, Object[] args, Object returnObj, long totalTimeMillis) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ApiLog apiLog = method.getAnnotation(ApiLog.class);
        printLogMsg(apiLog.name(), apiLog.description(), apiLog.printReturn(), joinPoint, args, returnObj, totalTimeMillis);
    }
}
