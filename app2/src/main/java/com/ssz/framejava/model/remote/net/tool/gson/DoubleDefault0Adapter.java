package com.ssz.framejava.model.remote.net.tool.gson;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @author zsp
 * create at 2019/1/18 9:50
 * 自定义解析double类型,如果后台返回""或者null,则返回0.00
 */
public class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.i("adapter", "DoubleDefault0Adapter");
        try {
            if ("".equals(json.getAsString()) || "null".equals(json.getAsString())) {
                return 0.00;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsDouble();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
