package com.softfn.dev.components.tenant;

import com.softfn.dev.common.annotation.InjectTenant;
import com.softfn.dev.common.exception.BaseException;
import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.common.interfaces.Tenant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * <p/>
 * MultiTenantAspect 多租户schema拦截器
 * <p/>
 *
 * @author softfn
 */
public class MultiTenantAspect {
    public static final Logger logger = LoggerFactory.getLogger(MultiTenantAspect.class);

    @SuppressWarnings("Duplicates")
    public Object injection(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            beforeInvoke(joinPoint);
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            throw e;
        } finally {
            afterInvoke(joinPoint);
        }
    }

    public void beforeInvoke(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InjectTenant it = signature.getMethod().getAnnotation(InjectTenant.class);

        if (it != null) {
            String tenant = it.value();
            if (!StringUtils.hasText(tenant)) {
                tenant = getTenantFromParam(joinPoint);
                if (!StringUtils.hasText(tenant)) {
                    String msg = "注解了'InjectTenant'的方法{0}未定义租户参数";
                    throw new BaseException(ExceptionCode.INVALID_PARAM, MessageFormat.format(msg, joinPoint.getStaticPart()));
                }
            }
            String msg = "--租户Schema变量注入 --位置：{0} --变量：schema = {1}";
            logger.debug(MessageFormat.format(msg, joinPoint.getStaticPart(), tenant));
            TenantHelper.put(tenant);
        }
    }

    public void afterInvoke(JoinPoint joinPoint) throws Throwable {
        String schema = TenantHelper.remove();
        logger.debug(MessageFormat.format("--租户Schema变量清除 --位置：{0} --变量：schema = {1}", joinPoint.getStaticPart(), schema));
    }

    private String getTenantFromParam(JoinPoint joinPoint) {
        String tenant = "";
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Tenant) {
                tenant = ((Tenant) arg).getSchema();
                break;
            }
        }
        return tenant;
    }
}
