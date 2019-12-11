package com.ssz.framejava.T.type.GenericArrayType;


import com.ssz.framejava.T.TypeUtils;
import com.ssz.framejava.T.type.Custom;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zsp
 * time : 2019 11 2019/11/21 13:50
 */
public class TypeTest<T, V extends @Custom Number> {

    private Number number;
    public T t;
    public V v;
    public List<T> list = new ArrayList<>();
    public Map<String, T> map = new HashMap<>();

    public T[] tArray;
    public List<T>[] ltArray;

    public TypeTest testClass;
    public TypeTest<T, Integer> testClass2;

    public Map<? super String, ? extends Number> mapWithWildcard;

    //泛型构造函数,泛型参数为X
    public <X extends Number> TypeTest(X x, T t) {
        number = x;
        this.t = t;
    }

    //泛型方法，泛型参数为Y
    public <Y extends T> void method(Y y) {
        t = y;
    }

    /*************************************************/

    interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {
        //返回此类型参数的上界列表，如果没有上界则放回Object. 例如  V extends @Custom Number & Serializable 这个类型参数，有两个上界，Number 和 Serializable
        Type[] getBounds();

        //类型参数声明时的载体，例如 `class TypeTest<T, V extends @Custom Number & Serializable>` ，那么V 的载体就是TypeTest
        D getGenericDeclaration();

        String getName();
        //Java 1.8加入 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
//        AnnotatedType[] getAnnotatedBounds();
    }

    //所有可以申明泛型参数的entities都必须实现这个接口
    public interface GenericDeclaration extends AnnotatedElement {
        public TypeVariable<?>[] getTypeParameters();
    }

    public static void main(String[] args) {
        try {
            Field field = TypeTest.class.getField("v");
            System.out.println(field.getGenericType());
            System.out.println(TypeUtils.getType(field.getGenericType()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
