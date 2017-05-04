package com.softfn.dev.components.token.aop;

import com.softfn.dev.components.token.service.Token;

/**
 * <p/>
 * TokenHolder APO范围内使用
 * <p/>
 *
 * @author softfn
 */
public class TokenHolder {
    /**
     * 获取当前Token
     *
     * @return
     */
    public static Token get() {
        return TokenThreadContext.get();
    }

    /**
     * 设置当前Token
     *
     * @param value Token
     * @return
     */
    public static Token put(Token value) {
        return TokenThreadContext.put(value);
    }

    /**
     * 清除当前Token
     *
     * @return
     */
    public static Token remove() {
        return TokenThreadContext.remove();
    }
}
