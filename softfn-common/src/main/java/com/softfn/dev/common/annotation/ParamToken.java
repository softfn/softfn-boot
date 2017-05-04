package com.softfn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * <p/>
 * ParamToken token参数注解
 * <p/>
 *
 * @author softfn
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamToken {
}
