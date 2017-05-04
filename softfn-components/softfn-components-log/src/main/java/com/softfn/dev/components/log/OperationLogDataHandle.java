package com.softfn.dev.components.log;

/**
 * <p>
 * OperationLogDataHandle 操作日志数据处理接口
 * </p>
 *
 * @author softfn
 */
public interface OperationLogDataHandle {
    /**
     * 保存日志
     *
     * @param logInfo
     */
    void saveLog(OperationLogInfo logInfo);
}
