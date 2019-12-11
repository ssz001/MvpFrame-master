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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author zsp
 * create at 2019/1/18 9:35
 * Gson核心的转换工厂
 */
public final class GsonConverterFactory extends Converter.Factory {

  public static GsonConverterFactory create() {
    return create(new Gson());
  }

  @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
  public static GsonConverterFactory create(Gson gson) {
    if (gson == null){
      throw new NullPointerException("gson == null");
    }
    return new GsonConverterFactory(gson);
  }

  private final Gson gson;

  private GsonConverterFactory(Gson gson) {
    this.gson = gson;
  }

  /**
   * responseBodyConverter（）方法里可以添加具体的逻辑
   * 若return null，表示不用这个转换器
   */
  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                          Retrofit retrofit) {
    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
    return new GsonResponseBodyConverter<>(gson, adapter);
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
    return new GsonRequestBodyConverter<>(gson, adapter);
  }
}
