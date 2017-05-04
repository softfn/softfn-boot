package com.softfn.dev.common.util.spring;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.softfn.dev.common.beans.BaseResponse;
import com.softfn.dev.common.util.lang.CustomSerializeConfig;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * <p/>
 * MessageConverterFastJson  FastJson消息转换器
 * <p/>
 *
 * @author softfn
 */
public class MessageConverterFastJson extends FastJsonHttpMessageConverter {
    public MessageConverterFastJson() {
        super();
        this.getFastJsonConfig().setSerializeConfig(new CustomSerializeConfig());
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (obj instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) obj;
            String callback = response.getCallback();
            if (StringUtils.hasText(callback)) {
                JSONPObject jsonpObject = new JSONPObject(callback);
                jsonpObject.addParameter(response);
                obj = jsonpObject;
            }
        }
        super.writeInternal(obj, outputMessage);
    }
}
