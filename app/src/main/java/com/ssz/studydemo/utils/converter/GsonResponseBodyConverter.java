/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssz.studydemo.utils.converter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author zsp
 * create at 2019/1/11 12:30
 * Gson 转换工厂
 * 请求返回的json数据 在这个类进行转换；
 * 若返回的json数据是加密的，可以在这里进行解密后在解析
 * 若返回的数据不规则，也可以在这里进行处理
 *  eg：data 在BaseResponse中泛型指定为T：
 *  返回错误数据如下：Gson 解析将出现异常；
 *  {
 *     "code":200,
 *     "message":"查询成功",
 *     "data": "查询成功"   或者   "data" : "null" 等
 *   }
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  /**
   * return selfConverter(value);   //解决data == "null" || ""
   * return systemConvert(value);   //默认的
   */
  @Override public T convert(@NonNull ResponseBody value) throws IOException {
     return systemConvert(value);
  }

  /**
   * Gson默认的转换规则
   */
  private T systemConvert(ResponseBody value) throws IOException{
    JsonReader jsonReader = gson.newJsonReader(value.charStream());
    try {
      return adapter.read(jsonReader);
    } finally {
      value.close();
    }
  }

  /**
   * 数据正常的解析才走这里，http changeStatus（code） 在[200,300) 外的请求不走这里
   * 自定义解决后台返回 data == null 不规范的问题
   * data == null 可能会有问题，无法存入JSONObject
   * data == "null" 或者 data =="" 可以；
   */
  private T selfConverter(ResponseBody value) throws IOException{
    String json = value.string();
    Log.i("value",json);
    try {
      JSONObject jsonObject = new JSONObject(json);
      if ("null".equals(jsonObject.get("data")) || "".equals(jsonObject.get("data"))){
          jsonObject.put("data",new JSONObject());
      }
      return adapter.fromJson(jsonObject.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }finally {
      value.close();
    }
    return adapter.fromJson(json);
  }
}
