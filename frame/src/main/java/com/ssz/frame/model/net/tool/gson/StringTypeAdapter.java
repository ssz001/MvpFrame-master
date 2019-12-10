package com.ssz.frame.model.net.tool.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author zsp
 * create at 2019/1/18 9:51
 * 自定义解析String类型
 * 未测试效果！
 */
public class StringTypeAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null || "null".equals(value)) {
            out.value("");
            return;
        }
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return "";
        }
        return in.nextString();
    }
}
