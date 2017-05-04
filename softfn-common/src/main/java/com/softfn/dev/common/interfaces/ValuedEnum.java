package com.softfn.dev.common.interfaces;

import com.alibaba.fastjson.EnumSerializer;

/**
 * <p/>
 * ValuedEnum code接口，辅助类型转换用
 * <p/>
 *
 * @author softfn
 */
public interface ValuedEnum extends EnumSerializer {
    int value();
}
