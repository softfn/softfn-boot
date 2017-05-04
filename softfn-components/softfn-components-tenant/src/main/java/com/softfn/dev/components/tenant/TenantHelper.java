package com.softfn.dev.components.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Stack;

/**
 * <p/>
 * TenantHelper
 * <p/>
 *
 * @author softfn
 */
public class TenantHelper {
    private final static Logger logger = LoggerFactory.getLogger(TenantHelper.class);

    /**
     * 租户注入
     * @param tenant 租户schema
     * @param wrapper 代理服务接口
     * @param <T>
     * @return
     */
    public static <T> T execService(String tenant, ServiceWrapper<T> wrapper) {
        printTenantStack("注入前");
        put(tenant);
        printTenantStack("注入后");
        try {
            return wrapper.invoke();
        } finally {
            remove();
            printTenantStack("清除后");
        }
    }

    /**
     * 获取当前租户schema
     * @return
     */
    public static String get() {
        return TenantThreadContext.get();
    }

    /**
     * 设置当前租户schema
     * @param value
     * @return
     */
    public static String put(String value) {
        return TenantThreadContext.put(value);
    }

    /**
     * 清除当前租户schema
     * @return
     */
    public static String remove() {
        return TenantThreadContext.remove();
    }

    private static void printTenantStack(String prefix) {
        if (logger.isDebugEnabled()) {
            Stack<String> localStack = TenantThreadContext.getLocalStack();
            StringBuffer sb = new StringBuffer();
            for (Iterator<String> iterator = localStack.iterator(); iterator.hasNext(); ) {
                String stack = iterator.next();
                sb.append(stack);
                if (iterator.hasNext())
                    sb.append(",");
            }
            logger.debug(MessageFormat.format("{0}租户线程变量的堆栈信息： {1}", prefix, sb));
        }
    }

    public interface ServiceWrapper<T> {
        T invoke();
    }
}
