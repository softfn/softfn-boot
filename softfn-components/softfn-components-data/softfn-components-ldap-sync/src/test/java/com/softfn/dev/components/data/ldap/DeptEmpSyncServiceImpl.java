package com.softfn.dev.components.data.ldap;

import com.softfn.dev.components.data.ldap.dto.DataPacket;
import com.softfn.dev.components.data.ldap.dto.LdapDept;
import com.softfn.dev.components.data.ldap.dto.LdapEmp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * DeptEmpSyncServiceImpl
 * <p/>
 *
 * @author softfn
 */
@Service("orgEmpSyncService")
public class DeptEmpSyncServiceImpl implements DeptEmpSyncService {
    @Override
    public List<LdapDept> getAllDepts() {
        return new ArrayList<>();
    }

    @Override
    public Boolean updateDepts(DataPacket<LdapDept> orgDataPacket) {
        return true;
    }

    @Override
    public List<LdapEmp> getEmpsByDeptId(String orgId) {
        return new ArrayList<>();
    }

    @Override
    public Boolean updateEmps(DataPacket<LdapEmp> empDataPacket) {
        return false;
    }
}
