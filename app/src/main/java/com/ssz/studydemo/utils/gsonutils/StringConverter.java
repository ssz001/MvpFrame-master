package com.ssz.studydemo.utils.gsonutils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author zsp
 * create at 2019/1/23 13:54
 *  自定义了Gson的转换规则
 *  解决String 为 null Gson转换出错 ，null 统一 赋值为 ""，只能用于String字段 eg name ：null  -->  name : ""
 */
public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null || "null".equals(src) || "(null)".equals(src) ? new JsonPrimitive("") : new JsonPrimitive(src.toString());
    }

    @Override
    public String deserialize(JsonElement json, Type typeofT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}
