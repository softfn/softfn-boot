package com.softfn.dev.common.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoRetryService {
    private static final int RETRY_WAITING = 1000;
    private static final int RETRY_COUNT = 3;
    private static Logger logger = LoggerFactory.getLogger(AutoRetryService.class);

    public static void retry(AutoRetryAction action) throws Exception {
        // 异常重试标识
        boolean retryFlag = false;

        // 重试次数计数
        int retry = 0;
        do {
            // 数据同步
            try {
                action.run();

                retryFlag = false;
            } catch (Exception e1) {
                retryFlag = true;
                retry++;
            }

            if (retryFlag == false) {
                // 成功了
                break;
            }

            // 如果重试标志为true,并且已重试次数小于系统允许重试次数
            if (retryFlag && retry < RETRY_COUNT + 1) {//
                logger.info("#*接口执行失败，程序会在" + RETRY_WAITING * retry + "毫秒后尝试第"
                        + retry + "次重试，共重试" + RETRY_COUNT + "次");
                try {
                    // 线程等待
                    Thread.sleep(RETRY_WAITING * retry);
                } catch (InterruptedException e) {
                    logger.error("sleep error!~", e);
                }
            } else {
                throw new Exception("任务已重试" + retry + "次，但还是失败！");
            }
        } while (retryFlag);// 发生异常时可进行重试
    }
}
