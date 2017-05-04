package com.softfn.dev.components.converter.json;

import com.softfn.dev.common.interfaces.StatusCode;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;

/**
 * <p/>
 * CustomObjectMapper 自定义JSON序列化数据转化
 * <p/>
 *
 * @author softfn
 */
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addGenericMapping(StatusCode.class, new JsonSerializer<StatusCode>() {
            @Override
            public void serialize(StatusCode value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeNumber(value.code());
            }
        });
        this.setSerializerFactory(factory);
    }
}
