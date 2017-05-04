package com.softfn.dev.components.data.ldap;

import com.softfn.dev.components.data.ldap.dto.DataPacket;
import com.softfn.dev.components.data.ldap.dto.LdapEmp;
import com.softfn.dev.components.data.ldap.dto.LdapDept;

import java.util.List;

/**
 * <p/>
 * DeptEmpSyncService 部门与员工同步接口
 * <p/>
 * 实现由业务系统按需实现
 *
 * @author softfn
 */
public interface DeptEmpSyncService {
    /**
     * 获取所有部门数据
     *
     * @return 返回 部门列表信息
     */
    List<LdapDept> getAllDepts();

    /**
     * 批量更新部门数据
     *
     * @param orgDataPacket 部门数据包
     * @return 成功或失败
     */
    Boolean updateDepts(DataPacket<LdapDept> orgDataPacket);

    /**
     * 根据部门标识获取该节点下所有员工数据
     *
     * @param orgId 部门标识
     * @return 返回 员工列表信息
     */
    List<LdapEmp> getEmpsByDeptId(String orgId);

    /**
     * 更新员工数据
     *
     * @param empDataPacket 员工数据包
     * @return 成功或失败
     */
    Boolean updateEmps(DataPacket<LdapEmp> empDataPacket);
}
