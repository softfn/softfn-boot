package com.softfn.dev.components.data.ldap.impl;

import com.softfn.dev.common.annotation.InvokeLog;
import com.softfn.dev.components.data.ldap.dto.LdapDept;
import com.softfn.dev.components.data.ldap.dto.LdapEmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;


/**
 * <p/>
 * DeptEmpLdapServiceImpl 部门与员工LDAP服务
 * <p/>
 *
 * @author softfn
 */
public class DeptEmpLdapServiceImpl implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DeptEmpLdapServiceImpl.class);
    public static final String DEFAULT_DEPT_BASE_DN = "ou=departmentNumber,o=softfninfo";
    public static final String DEFAULT_EMP_BASE_DN = "ou=People,o=softfn.com.cn,o=isp";

    @Autowired
    private LdapTemplate ldapTemplate;
    /**
     * LDAP部门搜索根
     */
    private String deptBaseDN = DEFAULT_DEPT_BASE_DN;
    /**
     * LDAP员工搜索根
     */
    private String empBaseDN = DEFAULT_EMP_BASE_DN;

    /**
     * 获取LDAP里所有组织机构数据
     *
     * @return
     */
    @InvokeLog(name = "调用getAllDepts", description = "获取LDAP里所有组织机构数据", printReturn = false)
    public List<LdapDept> getAllDepts() {
        LdapQuery query = query()
                .base(deptBaseDN)
                .where("objectclass").is("organization");
        List<LdapDept> deptBriefs = ldapTemplate.search(query, new AttributesMapper<LdapDept>() {
            public LdapDept mapFromAttributes(Attributes attrs) throws NamingException {
                String id = getLdapAttrValue(attrs, "departmentNumber");
                String parentId = getLdapAttrValue(attrs, "softfn-parentId");
                String deptName = getLdapAttrValue(attrs, "displayName");
                String fullDeptName = getLdapAttrValue(attrs, "softfn-departmentName");
                String displayId = getLdapAttrValue(attrs, "softfn-displayId");
                int seq = Integer.parseInt(StringUtils.hasText(displayId) ? displayId : "0");
                LdapDept dept = new LdapDept();
                dept.setId(id);
                dept.setName(deptName);
                dept.setCode(fullDeptName);
                dept.setPid(parentId);
                dept.setSeq(seq);
//                dept.setExtend("{id:'" + id + "',fullDeptName:'" + fullDeptName + "'}");
                logger.trace("==== 从LDAP服务器获取部门：" + dept);
                return dept;
            }
        });
        logger.info("==== 从LDAP服务器查询部门记录总计 " + deptBriefs.size() + " 条");
        return deptBriefs;
    }

    /**
     * 根据部门ID获取LDAP员工数据
     *
     * @param deptId 部门ID
     * @return
     */
    @InvokeLog(name = "调用getEmpsByOrgId", description = "根据部门ID获取LDAP员工数据", printReturn = false)
    public List<LdapEmp> getEmpsByDeptId(String deptId) {
        LdapQuery query = query().base(empBaseDN)
                .where("objectclass").is("person")
                .and("departmentNumber").is(deptId)
                //员工状态，代号含义：0 - 未入司,1 - 试用期,2 - 正式,3 - 离职,4 - 退休,5 - 内退,6 - 离休,7 - 待岗,8 - 实习,9 - 调动中,10 - 派遣工
                .and(query().where("softfn-empstatus").is("0")
                        .or("softfn-empstatus").is("1")
                        .or("softfn-empstatus").is("2")
                        .or("softfn-empstatus").is("7")
                        .or("softfn-empstatus").is("8")
                        .or("softfn-empstatus").is("9")
                        .or("softfn-empstatus").is("10")
                );

        List<LdapEmp> empBriefs = ldapTemplate.search(query, new AttributesMapper<LdapEmp>() {
            public LdapEmp mapFromAttributes(Attributes attrs) throws NamingException {
                String gender = getLdapAttrValue(attrs, "softfn-gender"); // 0 - 未知 1 – 男 / 2 – 女
                String sex = gender == null ? "0" : gender;
                LdapEmp ldapEmp = new LdapEmp();
                ldapEmp.setId(getLdapAttrValue(attrs, "uid"));
                ldapEmp.setName(getLdapAttrValue(attrs, "sn"));
                ldapEmp.setUsername(getLdapAttrValue(attrs, "uid"));
                ldapEmp.setDeptId(getLdapAttrValue(attrs, "departmentNumber"));
                ldapEmp.setSex(sex);
                ldapEmp.setMail(getLdapAttrValue(attrs, "mail"));
                ldapEmp.setMobile(getLdapAttrValue(attrs, "mobile"));
                ldapEmp.setPositionName(getLdapAttrValue(attrs, "softfn-positionName"));
                ldapEmp.setPostalAddress(getLdapAttrValue(attrs, "postalAddress"));
                String employeeNumber = getLdapAttrValue(attrs, "employeeNumber");
                String rank = getLdapAttrValue(attrs, "softfn-reserve8");
                String fullDeptName = getLdapAttrValue(attrs, "softfn-departmentName");
                ldapEmp.setExtend("{rank:'" + rank + "', fullDeptName:'" + fullDeptName + "', employeeNumber:'" + employeeNumber + "'}");
                logger.trace("==== 从LDAP服务器获取员工：" + ldapEmp);
                return ldapEmp;
            }
        });

        logger.info("==== 根据部门标识 '" + deptId + "' 从LDAP服务器查询员工记录共计 " + empBriefs.size() + " 条");
        return empBriefs;
    }

    private String getLdapAttrValue(Attributes attrs, String attrID) throws NamingException {
        Attribute attribute = attrs.get(attrID);
        return attribute == null ? null : String.valueOf(attribute.get());
    }


    public void setDeptBaseDN(String deptBaseDN) {
        this.deptBaseDN = deptBaseDN;
    }

    public void setEmpBaseDN(String empBaseDN) {
        this.empBaseDN = empBaseDN;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
