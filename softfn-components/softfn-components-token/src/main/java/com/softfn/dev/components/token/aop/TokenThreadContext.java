package com.softfn.dev.components.token.aop;

import com.softfn.dev.components.token.service.Token;

import java.util.Stack;

/**
 * <p/>
 * TokenThreadContext
 * <p/>
 *
 * @author softfn
 */
final class TokenThreadContext {
    private static final ThreadLocal<Stack<Token>> localStack = new InheritableThreadLocal<>();

    private TokenThreadContext() {
    }

    static Token get() {
        Stack<Token> stack = getLocalStack();
        if (stack.size() > 0)
            return stack.peek();
        return null;
    }

    static Token put(Token value) {
        return getLocalStack().push(value);
    }

    static Token remove() {
        Stack<Token> stack = getLocalStack();
        if (stack.size() > 0)
            return stack.pop();
        return null;
    }

    static Stack<Token> getLocalStack() {
        Stack<Token> stack = localStack.get();
        if (stack == null) {
            localStack.set(stack = new Stack<>());
        }
        return stack;
    }

    static void clear() {
        localStack.remove();
    }
}
