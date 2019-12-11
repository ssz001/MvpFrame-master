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
 * 自定义解析String类型
 */
public class StringDefaultAdapter implements JsonSerializer<String>, JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.i("adapter", "StringDefaultAdapter");
        try {
            if ("null".equals(json.getAsString())) {
                return "";
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsString();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
