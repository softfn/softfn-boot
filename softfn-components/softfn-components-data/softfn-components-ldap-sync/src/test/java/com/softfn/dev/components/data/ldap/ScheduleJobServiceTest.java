package com.softfn.dev.components.data.ldap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p/>
 * ScheduleJobServiceTest
 * <p/>
 *
 * @author softfn
 */
public class ScheduleJobServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ScheduleJobService jobService = context.getBean("scheduleJobService", ScheduleJobService.class);
        jobService.startup();
    }
}
