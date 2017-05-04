package com.softfn.dev.components.token.aop;

import com.alibaba.dubbo.common.utils.Assert;
import com.softfn.dev.common.annotation.ParamToken;
import com.softfn.dev.common.annotation.TokenHandler;
import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.common.exception.LogLevel;
import com.softfn.dev.components.token.exception.TokenException;
import com.softfn.dev.components.token.service.Token;
import com.softfn.dev.components.token.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * <p/>
 * TokenAspect Token拦截器
 * <p/>
 *
 * @author softfn
 */
public class TokenAspect implements InitializingBean {
    public static final String ACCESS_TOKEN_HEADER_PARAM = "accessToken";
    @Autowired
    private TokenService tokenService;

    @SuppressWarnings("Duplicates")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
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
        TokenHandler tokenHandlerAnnotation = signature.getMethod().getAnnotation(TokenHandler.class);
        String tokenParam = tokenHandlerAnnotation.value();

        if (!StringUtils.hasText(tokenParam)) {
            tokenParam = getTokenParam(joinPoint);
        }

        Token token = tokenService.getToken(tokenParam);

        TokenHolder.put(token);
    }

    public void afterInvoke(JoinPoint joinPoint) throws Throwable {
        TokenHolder.remove();
    }

    private String getTokenParam(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER_PARAM);

        if (StringUtils.hasText(accessToken))
            return accessToken;

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == ParamToken.class) {
                    String tokenId = String.valueOf(args[i]);
                    return "null".equals(tokenId) ? null : tokenId;
                }
            }
        }
        throw new TokenException(ExceptionCode.TOKEN_EXPIRED_OR_INVALID, LogLevel.info);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(tokenService, "未找到TokenService实现类");
    }
}
