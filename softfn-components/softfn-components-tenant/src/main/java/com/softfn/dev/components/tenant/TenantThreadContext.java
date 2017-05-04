package com.softfn.dev.components.tenant;

import java.util.Stack;

/**
 * <p/>
 * TenantThreadContext
 * <p/>
 *
 * @author softfn
 */
final class TenantThreadContext {
    private static final ThreadLocal<Stack<String>> localStack = new ThreadLocal<Stack<String>>();

    private TenantThreadContext() {
    }

    static String get() {
        Stack<String> stack = getLocalStack();
        if (stack.size() > 0)
            return stack.peek();
        return null;
    }

    static String put(String value) {
        return getLocalStack().push(value);
    }

    static String remove() {
        Stack<String> stack = getLocalStack();
        if (stack.size() > 0)
            return stack.pop();
        return null;
    }

    /*synchronized*/
    static Stack<String> getLocalStack() {
        Stack<String> stack = localStack.get();
        if (stack == null) {
            localStack.set(stack = new Stack<String>());
        }
        return stack;
    }

    static void clear() {
        localStack.remove();
    }
}
