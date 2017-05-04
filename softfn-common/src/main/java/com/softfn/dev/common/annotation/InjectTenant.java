package com.softfn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * <p/>
 * InjectTenant 注入租户注解
 * <p/>
 *
 * @author softfn
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectTenant {
    String value() default "";
}
