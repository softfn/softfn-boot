package com.softfn.dev.common.util;

import com.softfn.dev.common.beans.BaseRequest;
import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.exception.BaseException;
import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.common.util.lang.FastJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * <p/>
 * ServiceHelper 服务辅助类
 * <p/>
 *
 * @author softfn
 * @deprecated
 */
public class ServiceHelper {
    private final static Logger logger = LoggerFactory.getLogger(ServiceHelper.class);

    private static void printBaseException(String methodName, BaseRequest req, Exception e) {
        logger.error("服务异常: 请求方法 = {} 请求参数 = {} 异常信息 = {}", methodName, serialize(req), e);
    }

    public static void execService(String methodName, final BaseRequest req, final BaseResponse res, ServiceWrapper wrapper) {
        StopWatch clock = new StopWatch();
        clock.start();
        String checkResult = req.checkParam();
        if (StringUtils.isBlank(checkResult)) {
            try {
                wrapper.invoke();
            } catch (BaseException e) {
                res.setCode(e.getStatus());
                res.setMsg(e.getMessage());
                printBaseException(methodName, req, e);
            } catch (Exception e) {
                res.setCode(ExceptionCode.EXCEPTION);
                res.setMsg(e.getMessage());
                printBaseException(methodName, req, e);
            }
        } else {
            res.setCode(ExceptionCode.INVALID_PARAM);
            res.setMsg(checkResult);
        }
        clock.stop();
        String info = "调用服务: 请求方法 = {} 请求参数 = {} 响应数据 = {} 共耗时 = {} ms";
        logger.info(info, methodName, serialize(req), serialize(res), clock.getTotalTimeMillis());
    }

    private static <T> String serialize(T object) {
        return FastJsonUtil.serialize(object);
    }

    public interface ServiceWrapper {
        void invoke();
    }
}
