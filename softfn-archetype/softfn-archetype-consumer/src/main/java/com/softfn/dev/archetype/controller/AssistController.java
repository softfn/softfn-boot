package com.softfn.dev.archetype.controller;

import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.constants.ResponseCode;
import com.softfn.dev.common.interfaces.StatusCode;
import com.softfn.dev.common.util.lang.LoadPackageClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * <p/>
 * AssistController
 * <p/>
 *
 * @author softfn
 */
@Controller
@RequestMapping("/system/assist")
public class AssistController {
    @Autowired
    private LoadPackageClasses loadPackageClasses;

    @RequestMapping(value = "/statuscode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public BaseResponse statusCode() {
        BaseResponse response = new BaseResponse();
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        try {
            for (Class clazz : loadPackageClasses.getClassSet()) {
                Method method = clazz.getMethod("values");
                if (method != null) {
                    Object[] statusCodes = (Object[]) method.invoke(null, null);
                    for (Object statusCode : statusCodes) {
                        if (statusCode instanceof StatusCode) {
                            StatusCode code = (StatusCode) statusCode;
                            map.put(code.code(), code.msg());
                        }
                    }
                }
            }
        } catch (Exception e) {
            response.setCode(ResponseCode.FAIL);
            response.setMsg(e.getMessage());
        }
        response.setData(map);
        return response;
    }
}
