package com.softfn.dev.archetype.service;


import com.softfn.dev.archetype.model.City;
import com.softfn.dev.common.beans.Page;

public interface CityService {
    City getCityById(Integer id);

    Page<City> findCities(Integer pageNum, Integer pageSize);
}
