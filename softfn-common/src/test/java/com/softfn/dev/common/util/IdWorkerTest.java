package com.softfn.dev.common.util;

import com.softfn.dev.common.util.lang.IdWorker;
import com.softfn.dev.common.util.lang.UUIDUtil;

import java.util.Set;

/**
 * <p/>
 * IdWorkerTest
 * <p/>
 *
 * @author softfn
 */
public class IdWorkerTest {

    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorker idWorker;

        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
            this.set = set;
            this.idWorker = idWorker;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long id = idWorker.nextId();
                System.out.println(id);
                if (!set.add(id)) {
                    System.out.println("duplicate:" + id);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        Set<Long> set = new HashSet<Long>();
//        final IdWorker idWorker1 = new IdWorker(4);
//        final IdWorker idWorker2 = new IdWorker(1);
//        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
//        Thread t2 = new Thread(new IdWorkThread(set, idWorker2));
//        t1.setDaemon(true);
//        t2.setDaemon(true);
//        t1.start();
//        t2.start();
//        try {
//            Thread.sleep(10000);
//            System.out.println("---" + set.size());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(new IdWorker(0).nextId());

        IdWorker idWorker = new IdWorker(1);

        System.out.println(idWorker.nextId());
        Thread.sleep(1);
        System.out.println(idWorker.nextId());

        System.out.println(UUIDUtil.randomId());
    }
}
