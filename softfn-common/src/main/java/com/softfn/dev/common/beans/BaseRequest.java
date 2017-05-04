package com.softfn.dev.common.beans;

import com.softfn.dev.common.util.validator.ParamCheckUtil;

import java.io.Serializable;

/**
 * <p/>
 * BaseRequest 请求参数基类
 * <p/>
 *
 * @author softfn
 */
public class BaseRequest implements Serializable {
    public String checkParam() {
        return ParamCheckUtil.checkParam(this);
    }

    public void validate() {
        ParamCheckUtil.validate(this);
    }
}
