package com.softfn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * <p/>
 * InvokeLog 调用日志
 * <p/>
 *
 * @author softfn
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvokeLog {
    /**
     * 调用方法名称
     *
     * @return
     */
    String name() default "";

    /**
     * 描述
     *
     * @return
     */
    String description() default "";

    /**
     * 是否打印返回值
     *
     * @return
     */
    boolean printReturn() default true;
}
