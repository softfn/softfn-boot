package com.softfn.dev.archetype.service.impl;


import com.github.pagehelper.PageHelper;
import com.softfn.dev.archetype.mapper.CityMapper;
import com.softfn.dev.archetype.model.City;
import com.softfn.dev.archetype.model.CityExample;
import com.softfn.dev.archetype.service.CityService;
import com.softfn.dev.common.annotation.InjectTenant;
import com.softfn.dev.common.annotation.InvokeLog;
import com.softfn.dev.common.beans.Page;
import com.softfn.dev.components.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHANGWB11
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CacheService<String, City> cacheService;

    @Autowired
    private CityMapper cityMapper;

    @InvokeLog(name = "调用getCityById", description = "根据id查询城市信息")
    @InjectTenant("mycat")
    public City getCityById(Integer id) {
        return cityMapper.selectByPrimaryKey(id);
    }

    @InvokeLog(name = "调用findCities", description = "分页查询城市列表信息")
    public Page<City> findCities(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<City> cities = cityMapper.selectByExample(new CityExample());
        return new Page<City>(cities) ;
    }
}
