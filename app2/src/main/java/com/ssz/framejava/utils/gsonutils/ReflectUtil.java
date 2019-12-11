package com.ssz.framejava.utils.gsonutils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsp
 * create at 2018/12/28 8:09
 * Gson 解析工具类
 */
public class ReflectUtil {
    /**
     * 使用可以转换null 的gson对象，如name:null也可以转换出来
     */
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            // 时间转化为特定格式
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()// 防止网址被乱码掉
//             .excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
            .enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
            .setPrettyPrinting() // 对json结果格式化.
            // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//
            // 会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
            // .setVersion(1.0) //
            // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
            // @Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
            // @Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
            //防止String变量出现null值
            .registerTypeAdapter(String.class, new StringConverter())
            .create();

    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> reMap = new HashMap<>();
        Class<?> clz = obj.getClass();
        while (clz != null) {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    String fieldName = field.getName();
                    if (!extraParam(fieldName)) {
                        Field f = clz.getDeclaredField(fieldName);
                        f.setAccessible(true);
                        Object o = f.get(obj);
                        reMap.put(fieldName, o);
                    }
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    // Log.d("ReflectUtil").e("toMap NoSuchFieldException:" + e.getMessage());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    //Logger.t("ReflectUtil").e("toMap IllegalArgumentException:" + e.getMessage());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    //Logger.t("ReflectUtil").e("toMap IllegalAccessException:" + e.getMessage());
                }
            }
            clz = clz.getSuperclass();
        }
        return reMap;
    }

    private static boolean extraParam(String fieldName) {
        return "serialVersionUID".equals(fieldName) || "shadow$_klass_".equals(fieldName) || "shadow$_monitor_".equals(fieldName);
    }

    /**
     * @param obj 要解析的对象
     * @return 转化结果json字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return GSON.toJson(obj);
        } catch (Exception e) {
            Log.d("reflect","toJson Exception");
            return "";
        }
    }

    /**
     * 解析数据成具体一个对象
     *
     * @param json 字符串
     * @param obj  要解析成的对象
     * @return 将json字符串解析成obj类型的对象
     */
    public static <T> T toObj(String json, Class<T> obj) {
        if (obj == null || TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return GSON.fromJson(json, obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 不区分类型，传什么解析什么，例如new TypeToken<List<Person>>(){}.getType()
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonStr, Type type) {
        return GSON.fromJson(jsonStr, type);
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clz) {//解决类型擦除的问题
        List<T> list = GSON.fromJson(jsonStr, new MyType(clz));
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static <T> Map<String, T> toMap(String jsonStr, Class<T> clz) {
        Map<String, T> map = GSON.fromJson(jsonStr, new MyType(clz));
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 参数化类型
     */
    private static class MyType implements ParameterizedType {
        private Type type;

        private MyType(Type type) {
            this.type = type;
        }

        @NonNull
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @NonNull
        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
        // implement equals method too! (as per javadoc)
    }

}
