package com.softfn.dev.components.data.ldap.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p/>
 * LdapDept LDAP组织部门描述类
 * <p/>
 *
 * @author softfn
 */
public class LdapDept implements IdAware {
    /**
     * 部门ID
     */
    private String id;
    /**
     * 父部门ID
     */
    private String pid;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门编码
     */
    private String code;
    /**
     * 部门排序号
     */
    private int seq;
    /**
     * 扩展信息  存储业务系统部门关键信息  如部门ID，方便后续更新数据使用
     */
    private String extend;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

        LdapDept ldapDept = (LdapDept) o;

        if (seq != ldapDept.seq) return false;
        if (id != null ? !id.equals(ldapDept.id) : ldapDept.id != null) return false;
        if (pid != null ? !pid.equals(ldapDept.pid) : ldapDept.pid != null) return false;
        if (name != null ? !name.equals(ldapDept.name) : ldapDept.name != null) return false;
        return code != null ? code.equals(ldapDept.code) : ldapDept.code == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + seq;
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("pid", pid)
                .append("name", name)
                .append("code", code)
                .append("seq", seq)
                .append("extend", extend)
                .toString();
    }
}
