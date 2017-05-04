美的基础数据同步组件
-----------------

1）组织机构、员工数据同步（全量更新，包括新增、修改和删除三类数据）
    
    a) pom.xml 引入依赖
        <dependency>
            <groupId>com.softfn.dev</groupId>
            <artifactId>softfn-components-ldap-sync</artifactId>
        </dependency>
    
    b) 添加com.softfn.dev.components.data.ldap.OrgEmpSyncService接口实现
        <bean id="deptEmpSyncService" class="com.softfn.dev.xx.service.impl.DeptEmpSyncServiceImpl"/>
    
    c) 作业调度执行 ScheduleJobService 方法 startup() 即可
        
    