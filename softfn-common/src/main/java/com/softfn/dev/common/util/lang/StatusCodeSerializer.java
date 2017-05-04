package com.softfn.dev.common.util.lang;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.softfn.dev.common.interfaces.StatusCode;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * StatusCodeSerializer
 * </p>
 *
 * @author softfn
 */
class StatusCodeSerializer implements ObjectSerializer {
    public final static StatusCodeSerializer instance = new StatusCodeSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        StatusCode value = (StatusCode) object;

        if (value == null) {
            out.writeNull();
            return;
        }

        out.writeInt(value.code());
    }
}
