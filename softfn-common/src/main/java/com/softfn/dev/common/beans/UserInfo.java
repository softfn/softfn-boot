package com.softfn.dev.common.beans;

import com.alibaba.fastjson.annotation.JSONField;
import com.softfn.dev.common.interfaces.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

/**
 * <p/>
 * UserDetail 用户详细信息
 * <p/>
 *
 * @author softfn
 */
public class UserInfo implements User {
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码（加密后密钥）
     */
    private String password;
    /**
     * 照片
     */
    private String photo;
    /**
     * 账号是否过期
     */
    private boolean accountNonExpired;
    /**
     * 账号是否锁定
     */
    private boolean accountNonLocked;
    /**
     * 密钥是否过期
     */
    private boolean credentialsNonExpired;
    /**
     * 账号是否启用
     */
    private boolean enabled;
    /**
     * 令牌
     */
    private String token;
    /**
     * 租户标识
     */
    private String tenantId;
    /**
     * 租户逻辑库
     */
    private String schema;
    /**
     * 权限信息
     */
    private Collection<?> authorities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public Collection<?> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<?> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("username", username)
                .append("password", password)
                .append("photo", photo)
                .append("accountNonExpired", accountNonExpired)
                .append("accountNonLocked", accountNonLocked)
                .append("credentialsNonExpired", credentialsNonExpired)
                .append("enabled", enabled)
                .append("token", token)
                .append("tenantId", tenantId)
                .append("schema", schema)
                .append("authorities", authorities)
                .toString();
    }
}
