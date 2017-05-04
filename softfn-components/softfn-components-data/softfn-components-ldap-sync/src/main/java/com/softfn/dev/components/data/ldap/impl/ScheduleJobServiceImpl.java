package com.softfn.dev.components.data.ldap.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.softfn.dev.common.annotation.InvokeLog;
import com.softfn.dev.components.data.ldap.DeptEmpSyncService;
import com.softfn.dev.components.data.ldap.ScheduleJobService;
import com.softfn.dev.components.data.ldap.dto.DataPacket;
import com.softfn.dev.components.data.ldap.dto.IdAware;
import com.softfn.dev.components.data.ldap.dto.LdapDept;
import com.softfn.dev.components.data.ldap.dto.LdapEmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p/>
 * ScheduleJobServiceImpl 调度作业服务实现
 * <p/>
 *
 * @author softfn
 */
public class ScheduleJobServiceImpl implements ScheduleJobService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);
    public static final int THREADS_SIZE = 8;
    @Autowired
    private DeptEmpSyncService deptEmpSyncService;
    @Autowired
    private DeptEmpLdapServiceImpl deptEmpLdapService;

    @Override
    @InvokeLog(name = "调用startup", description = "LDAP部门及员工数据开始同步")
    public void startup() {
        logger.info("==== LDAP部门及员工数据开始同步");

        List<LdapDept> ldapDepts = deptEmpLdapService.getAllDepts();
        List<LdapDept> busDepts = deptEmpSyncService.getAllDepts();

        DataPacket<LdapDept> deptDataPacket = findDifference(ldapDepts, busDepts);
        // 更新部门数据
        deptEmpSyncService.updateDepts(deptDataPacket);

        // 更新员工数据
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_SIZE);
        final AtomicInteger empAddNum = new AtomicInteger(0);
        final AtomicInteger empEditNum = new AtomicInteger(0);
        final AtomicInteger empDelNum = new AtomicInteger(0);
        for (final LdapDept deptBrief : ldapDepts) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<LdapEmp> ldapEmps = deptEmpLdapService.getEmpsByDeptId(deptBrief.getId());
                    List<LdapEmp> busEmps = deptEmpSyncService.getEmpsByDeptId(deptBrief.getId());
                    final DataPacket<LdapEmp> empDataPacket = findDifference(ldapEmps, busEmps);
                    empAddNum.addAndGet(empDataPacket.getNewData().size());
                    empEditNum.addAndGet(empDataPacket.getDirtyData().size());
                    empDelNum.addAndGet(empDataPacket.getJunkData().size());
                    deptEmpSyncService.updateEmps(empDataPacket);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        String msg = "==== LDAP部门及员工数据同步完成:" +
                "\n\t\t 1)部门数据：新增 {0} 、更新 {1} 、删除 {2} 条;" +
                "\n\t\t 2)员工数据：新增 {3} 、更新 {4} 、删除 {5} 条";
        logger.info(MessageFormat.format(msg,
                deptDataPacket.getNewData().size(),
                deptDataPacket.getDirtyData().size(),
                deptDataPacket.getJunkData().size(),
                empAddNum.get(),
                empEditNum.get(),
                empDelNum.get()));
    }


    /**
     * 查询差异数据
     *
     * @param source 源列表数据
     * @param target 目标列表数据
     * @param <T>
     * @return
     */
    private <T extends IdAware> DataPacket<T> findDifference(List<T> source, List<T> target) {
        DataPacket<T> dataPacket = new DataPacket<>();
        Map<String, T> sourceMap = listToMap(source);
        for (T t : target) {
            T s = sourceMap.get(t.getId());
            if (s == null) { // 为空 说明LDAP不存在该数据，添加到废弃数据列表
                dataPacket.getJunkData().add(t);
            } else {
                if (s.equals(t)) { // 相等 说明该数据及LDAP是同步的不做任何处理
                    // do nothing
                } else { // 不相等 说明该数据为脏数据，添加到脏数据列表
                    dataPacket.getDirtyData().add(s);
                }
                sourceMap.remove(t.getId());
            }
        }
        dataPacket.getNewData().addAll(sourceMap.values()); // 剩下的数据添加到新数据列表

        return dataPacket;
    }

    private <T extends IdAware> Map<String, T> listToMap(List<T> list) {
        HashMap<String, T> map = new HashMap<>();
        for (T t : list) {
            map.put(t.getId(), t);
        }
        return map;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(deptEmpSyncService, "deptEmpSyncService未注入，确认应用是否实现DeptEmpSyncService服务接口？");
        Assert.notNull(deptEmpLdapService, "deptEmpLdapService未注入");
    }

}
