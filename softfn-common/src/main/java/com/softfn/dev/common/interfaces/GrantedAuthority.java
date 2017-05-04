package com.softfn.dev.common.interfaces;

import java.io.Serializable;

/**
 * <p/>
 * GrantedAuthority 授权信息接口
 * <p/>
 *
 * @author softfn
 */
public interface GrantedAuthority extends Serializable {
    /**
     * 获取权限信息
     * @return
     */
    String getAuthority();
}