package com.softfn.dev.archetype.controller;

import com.alibaba.fastjson.JSONPObject;
import com.softfn.dev.archetype.dto.GetCityRequest;
import com.softfn.dev.archetype.model.City;
import com.softfn.dev.archetype.service.CityService;
import com.softfn.dev.common.annotation.InvokeLog;
import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.beans.Page;
import com.softfn.dev.common.util.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author ZHANGWB11
 */
@Controller
@RequestMapping("/test")
public class CityController {
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
    @InvokeLog(name = "调用city", description = "根据id查找城市信息")
    @ResponseBody
    public BaseResponse city(@PathVariable("id") Integer id) {
        // 校验参数
        new GetCityRequest().setId(id).validate();

        BaseResponse response = new BaseResponse();
        City city = cityService.getCityById(id);
        response.setData(city);

        return response;
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    @InvokeLog(name = "调用cities", description = "分页查找城市信息")
    @ResponseBody
    public BaseResponse cities(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        BaseResponse response = new BaseResponse();
        Page<City> cities = cityService.findCities(pageNum, pageSize);
        response.setData(cities);
        return response;
    }

    @RequestMapping(value = "/jsonp/city", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject cities(@RequestParam(value = "id", required = true) final Integer id,
                        @RequestParam(value = "callback", required = true) String callback) {
        final BaseResponse rep = new BaseResponse();
        GetCityRequest req = new GetCityRequest();
        req.setId(id);
        // AOP做了日志和异常处理，以下方式不建议使用
        ServiceHelper.execService("users", req, rep, new ServiceHelper.ServiceWrapper() {
            public void invoke() {
                City city = cityService.getCityById(id);
                rep.setData(city);
            }
        });
        return rep.toJSONP(callback);
    }
}
