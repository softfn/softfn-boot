package com.softfn.dev.common.interfaces;

import java.util.Collection;

/**
 * <p/>
 * User 用户接口
 * <p/>
 *
 * @author softfn
 */
public interface User extends Tenant {

    /**
     * 获取用户权限信息
     */
    Collection<?> getAuthorities();

    /**
     * 获取用户名（账号）
     *
     * @return
     */
    String getUsername();

    /**
     * 获取用户密码
     *
     * @return
     */
    String getPassword();

    /**
     * 判定账号是否过期
     *
     * @return
     */
    boolean isAccountNonExpired();

    /**
     * 判定账号是否锁定
     *
     * @return
     */
    boolean isAccountNonLocked();

    /**
     * 判定密钥是否过期
     *
     * @return
     */
    boolean isCredentialsNonExpired();

    /**
     * 判定账号是否启用
     *
     * @return
     */
    boolean isEnabled();

    /**
     * 获取当前登录Token
     *
     * @return
     */
    String getToken();

}
