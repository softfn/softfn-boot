package com.softfn.dev.components.data.ldap.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * LdapEmp lDAP员工信息描述类
 * <p/>
 *
 * @author softfn
 */
public class LdapEmp implements IdAware {
    /**
     * 员工标识
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 帐号
     */
    private String username;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 性别（1 – 男 / 2 – 女）
     */
    private String sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 电子邮箱
     */
    private String mail;
    /**
     * 职位名称
     */
    private String positionName;
    /**
     * 邮寄地址
     */
    private String postalAddress;
    /**
     * 扩展信息，如存放业务系统员工信息对应的关键信息 员工ID
     */
    private String extend;

    public LdapEmp() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LdapEmp ldapEmp = (LdapEmp) o;

        if (!id.equals(ldapEmp.id)) return false;
        if (!name.equals(ldapEmp.name)) return false;
        if (!username.equals(ldapEmp.username)) return false;
        if (!deptId.equals(ldapEmp.deptId)) return false;
        if (!sex.equals(ldapEmp.sex)) return false;
        if (mobile != null ? !mobile.equals(ldapEmp.mobile) : ldapEmp.mobile != null) return false;
        if (mail != null ? !mail.equals(ldapEmp.mail) : ldapEmp.mail != null) return false;
        if (positionName != null ? !positionName.equals(ldapEmp.positionName) : ldapEmp.positionName != null)
            return false;
        return postalAddress != null ? postalAddress.equals(ldapEmp.postalAddress) : ldapEmp.postalAddress == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + deptId.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (positionName != null ? positionName.hashCode() : 0);
        result = 31 * result + (postalAddress != null ? postalAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("username", username)
                .append("deptId", deptId)
                .append("sex", sex)
                .append("mobile", mobile)
                .append("mail", mail)
                .append("positionName", positionName)
                .append("postalAddress", postalAddress)
                .append("extend", extend)
                .toString();
    }
}
