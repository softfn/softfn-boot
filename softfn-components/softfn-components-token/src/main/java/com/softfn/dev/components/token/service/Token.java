package com.softfn.dev.components.token.service;

import java.io.Serializable;
import java.util.Date;

/**
 * <p/>
 * Token 令牌接口
 * <p/>
 *
 * @author softfn
 */
public interface Token<T extends Serializable> extends Serializable {
    /**
     * 访问令牌
     *
     * @return
     */
    String getAccessToken();

    /**
     * 获取用户账号
     *
     * @return
     */
    String getUsername();

    /**
     * 获取创建时间
     *
     * @return
     */
    Date getCreateTime();

    /**
     * 获取内容明细
     *
     * @return
     */
    T getContent();
}
