package com.softfn.dev.common.util.lang;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.softfn.dev.common.interfaces.ValuedEnum;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * ValuedEnumSerializer
 * </p>
 *
 * @author softfn
 */
class ValuedEnumSerializer implements ObjectSerializer {
    public final static ValuedEnumSerializer instance = new ValuedEnumSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        ValuedEnum value = (ValuedEnum) object;

        if (value == null) {
            out.writeNull();
            return;
        }

        out.writeInt(value.value());
    }
}
