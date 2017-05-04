package com.softfn.dev.components.converter.controller;

import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.util.lang.FastJsonUtil;
import com.softfn.dev.common.util.ServiceHelper;
import com.softfn.dev.components.converter.dto.WsAdaptRequest;
import com.softfn.dev.components.converter.service.WsAdaptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p/>
 * ConverterController 转化控制器
 * <p/>
 *
 * @author softfn
 */
@Controller
@RequestMapping("/")
public class ConverterController {
    @Autowired
    private WsAdaptService wsAdaptService;

    @RequestMapping(value = "wsadapt", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<HashMap> wsAdapt(@RequestParam(value = "uri", required = true) final String uri,
                                         @RequestParam(value = "charset", required = false) final String charset) {
        final WsAdaptRequest req = new WsAdaptRequest(uri, charset);
        final BaseResponse<HashMap> res = new BaseResponse<HashMap>();
        ServiceHelper.execService("wsAdapt", req, res, new ServiceHelper.ServiceWrapper() {
            public void invoke() {
                String wsJson = wsAdaptService.getJsonDataFromWebservice(uri, charset);
                if (!StringUtils.isBlank(wsJson)) {
                    res.setData(FastJsonUtil.deserialize(wsJson, HashMap.class));
                }
            }
        });
        return res;
    }
}
