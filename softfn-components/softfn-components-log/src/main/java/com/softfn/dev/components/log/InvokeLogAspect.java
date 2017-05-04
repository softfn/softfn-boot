package com.softfn.dev.components.log;

import com.softfn.dev.common.annotation.InvokeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <p/>
 * InvokeLogAspect 普通调用日志AOP
 * <p/>
 *
 * @author softfn
 */
public class InvokeLogAspect extends AbstractPrintLog {
    protected void handleLog(ProceedingJoinPoint joinPoint, Object[] args, Object returnObj, long totalTimeMillis) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        InvokeLog invokeLog = method.getAnnotation(InvokeLog.class);
        printLogMsg(invokeLog.name(), invokeLog.description(), invokeLog.printReturn(), joinPoint, args, returnObj, totalTimeMillis);
    }
}
