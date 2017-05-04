package com.softfn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * OperationLog 操作日志
 * </p>
 *
 * @author softfn
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@InvokeLog
public @interface OperationLog {
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

    /**
     * 权限编码
     *
     * @return
     */
    String authority() default "";
}
