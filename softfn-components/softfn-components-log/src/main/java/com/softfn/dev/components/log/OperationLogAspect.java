package com.softfn.dev.components.log;

import com.softfn.dev.common.annotation.OperationLog;
import com.softfn.dev.common.util.lang.FastJsonUtil;
import com.softfn.dev.common.util.lang.IpAddressUtil;
import com.softfn.dev.common.util.lang.UrlUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p/>
 * OperationLogAspect 操作日志AOP
 * <p/>
 *
 * @author softfn
 */
public class OperationLogAspect extends AbstractPrintLog {
    @Autowired(required = false)
    private OperationLogDataHandle operationLogDataHandle;

    protected void handleLog(ProceedingJoinPoint joinPoint, Object[] args, Object returnObj, long totalTimeMillis) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        // 文件日志输出
        printLogMsg(operationLog.name(), operationLog.description(), operationLog.printReturn(), joinPoint, args, returnObj, totalTimeMillis);
        // 输入数据库
        if (operationLogDataHandle != null) {
            try {
                int costTime = new Long(totalTimeMillis).intValue();
                String authority = getVarString(operationLog.authority());
                logDataHandle(authority, method.toString(), costTime, args, FastJsonUtil.serialize(returnObj));
            } catch (Exception e) {
                logger.warn("操作日志输出DB异常", e);
            }
        }
    }

    private void logDataHandle(String authority, String method, int costTime, Object[] params, String returnObj) throws Exception {
        HttpServletRequest request = getHttpServletRequest();
        String path = UrlUtils.getLookupPathForRequest(request);
        OperationLogInfo logInfo = new OperationLogInfo();
        logInfo.setAuthority(authority);
        logInfo.setUri(path);
        logInfo.setMethod(method);
        logInfo.setAction(request.getMethod());
        logInfo.setIp(IpAddressUtil.getIpAddress(request));
        logInfo.setCostTime(costTime);
        logInfo.setOperateTime(new Date());
        logInfo.setParams(FastJsonUtil.serialize(argsDemote(params)));
        logInfo.setReturnObj(returnObj);
        operationLogDataHandle.saveLog(logInfo);
    }

    /**
     * 获取变量字符串
     *
     * @param template 模板
     * @return
     */
    private String getVarString(String template) {
        Map<String, String> varMap = (Map<String, String>) getHttpServletRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        //不存在变量
        if (varMap == null || varMap.size() == 0) {
            return template;
        }
        //获取authority中所有变量定义，变量定义：{var}
        Set<String> varDefines = getVarDefines(template);
        //不存在变量定义
        if (varDefines == null) {
            return template;
        }
        Iterator<String> itor = varDefines.iterator();
        while (itor.hasNext()) {
            //变量定义 :{platform}
            String varDef = itor.next();
            //变量名 :platform
            String varName = varDef.replace("{", "").replace("}", "").trim();
            if (varMap.containsKey(varName)) {
                template = template.replace(varDef, varMap.get(varName));
            }
        }

        return template;
    }

    /**
     * 从字符串中取出 所有变量定义， 变量定义 : {varName}
     *
     * @param str
     * @return 所有变量定义
     */
    private static Set<String> getVarDefines(String str) {
        if (str == null || str.equals("") || str.indexOf("{") == -1) {
            return null;
        }

        Set<String> vars = null;
        Pattern p = Pattern.compile("(\\{[^\\}]*\\})");
        Matcher m = p.matcher(str);

        while (m.find()) {
            if (vars == null) {
                vars = new HashSet<>();
            }
            vars.add(m.group());
        }

        return vars;
    }
}
