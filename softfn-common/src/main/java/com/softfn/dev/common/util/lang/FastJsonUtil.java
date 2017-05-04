package com.softfn.dev.common.util.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.softfn.dev.common.beans.BaseResponse;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

public class FastJsonUtil {
    public static final CustomSerializeConfig SERIALIZE_CONFIG = new CustomSerializeConfig();
    public static final SerializerFeature[] SERIALIZER_FEATURES = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.QuoteFieldNames,
            SerializerFeature.WriteDateUseDateFormat
    };

    /**
     * 将java类型的对象转换为JSON格式的字符串
     *
     * @param object java类型的对象
     * @return JSON格式的字符串
     */
    @SuppressWarnings("Duplicates")
    public static <T> String serialize(T object) {
        Object o = object;
        if (object instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) object;
            String callback = response.getCallback();
            if (StringUtils.hasText(callback)) {
                JSONPObject jsonpObject = new JSONPObject(callback);
                jsonpObject.addParameter(response);
                o = jsonpObject;
            }
        }
        return JSON.toJSONString(o, SERIALIZE_CONFIG, SERIALIZER_FEATURES);
    }

    /**
     * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
     *
     * @param json JSON格式的字符串
     * @param clz  java类型或者java数组类型，不包括java集合类型
     * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
     */
    public static <T> T deserialize(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }

    /**
     * 将JSON格式的字符串转换为List<T>类型的对象
     *
     * @param json JSON格式的字符串
     * @param clz  指定泛型集合里面的T类型
     * @return List<T>类型的对象
     */
    public static <T> List<T> deserializeList(String json, Class<T> clz) {
        return JSON.parseArray(json, clz);
    }

    /**
     * 将JSON格式的字符串转换成任意Java类型的对象
     *
     * @param json JSON格式的字符串
     * @param type 任意Java类型
     * @return 任意Java类型的对象
     */
    public static <T> T deserializeAny(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }


    public static String getJsonValue(String json, String key) {
        HashMap map = FastJsonUtil.deserialize(json, HashMap.class);
        return String.valueOf(map.get(key));
    }
}