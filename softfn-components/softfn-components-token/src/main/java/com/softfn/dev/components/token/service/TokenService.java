package com.softfn.dev.components.token.service;

import com.softfn.dev.components.token.exception.TokenException;

import java.io.Serializable;

/**
 * <p/>
 * TokenService  令牌服务接口
 * <p/>
 *
 * @author softfn
 */
public interface TokenService<T extends Serializable> {
    /**
     * 获取令牌
     *
     * @param accessToken 令牌
     * @return
     */
    Token<T> getToken(String accessToken) throws TokenException;

    /**
     * 生成令牌
     *
     * @param username 用户名
     * @param content  令牌内容
     * @return
     */
    String generateToken(String username, T content);

    /**
     * 销毁令牌
     *
     * @param accessToken 访问令牌
     */
    void destoryAccessToken(String accessToken);

    /**
     * 触发令牌 延长有效期
     *
     * @param accessToken 访问令牌
     */
    void touchAccessToken(String accessToken);
}
