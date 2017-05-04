package com.softfn.dev.archetype.dto;

import com.softfn.dev.common.beans.BaseRequest;

import javax.validation.constraints.NotNull;

/**
 * <p/>
 * GetCityRequest
 * <p/>
 *
 * @author softfn
 */
public class GetCityRequest extends BaseRequest {
    @NotNull(message = "id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public GetCityRequest setId(Integer id) {
        this.id = id;
        return this;
    }
}
