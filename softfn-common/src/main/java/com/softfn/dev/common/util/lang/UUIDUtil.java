package com.softfn.dev.common.util.lang;

import java.util.UUID;

/**
 * <p>
 * UUIDUtil UUID生成工具类
 * <p>
 *
 * @author softfn
 */
public class UUIDUtil {
    private static IdWorker idWorker = new IdWorker(0,0);

    /**
     * UUID随机生成
     *
     * @return 字符串
     */
    public static String randomId() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String id = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18)
                + str.substring(19, 23) + str.substring(24);
        return id;
    }

    /**
     * UUID生成
     *
     * @return 长整型
     */
    public static Long nextId() {
        return idWorker.nextId();
    }
}
