package com.softfn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * <p/>
 * TokenHandler 令牌处理器注解
 * <p/>
 *
 * @author softfn
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenHandler {
//    boolean validate() default true;

    String value() default "";
}
