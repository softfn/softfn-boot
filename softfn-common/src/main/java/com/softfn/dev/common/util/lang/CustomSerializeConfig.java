package com.softfn.dev.common.util.lang;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.softfn.dev.common.interfaces.StatusCode;
import com.softfn.dev.common.interfaces.ValuedEnum;

/**
 * <p>
 * CustomSerializeConfig
 * </p>
 *
 * @author softfn
 */
public class CustomSerializeConfig extends SerializeConfig {
    public CustomSerializeConfig() {
        super();
    }

    public CustomSerializeConfig(int tableSize) {
        super(tableSize);
    }

    @Override
    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        ObjectSerializer writer = this.get(clazz);
        if (writer == null) {
            if (StatusCode.class.isAssignableFrom(clazz)) {
                put(clazz, StatusCodeSerializer.instance);
            } else if (ValuedEnum.class.isAssignableFrom(clazz)) {
                put(clazz, ValuedEnumSerializer.instance);
            }
        }
        return super.getObjectWriter(clazz);
    }
}
