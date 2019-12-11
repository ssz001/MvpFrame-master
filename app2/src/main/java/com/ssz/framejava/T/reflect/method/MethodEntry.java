package com.ssz.framejava.T.reflect.method;


import com.ssz.framejava.T.reflect.annotation.AnnoMethod;
import com.ssz.framejava.T.reflect.annotation.AnnoMethod2;
import com.ssz.framejava.T.reflect.annotation.ParamType;
import com.ssz.framejava.T.reflect.annotation.ParamType2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author : zsp
 * time : 2019 11 2019/11/21 10:41
 */
public class MethodEntry {

    public static void main(String[] args) {
//       for (Method m : methods){
//           AnnoMethod am = m.getAnnotation(AnnoMethod.class);
//           ParamType pm = m.getAnnotation(ParamType.class);
//           System.out.println(m.getName());
//           System.out.println("am == null : " + (am == null));
//           System.out.println("pm == null : " + (pm == null));
//           // entry
//           // am == null : false
//           // pm == null : true
//       }
        getAnnotation();
        getDeclaredAnnotations();
        getDeclaringClass();
        getDefaultValue();// 不清楚干嘛的
        getExceptionTypes();
        getGenericExceptionTypes();
        getGenericParameterTypes();
        getGenericReturnType();
        getParameterAnnotations();
        getReturnType();
    }

    @AnnoMethod
    @AnnoMethod2
    public void entry(@ParamType @ParamType2 String name) {

    }

    @AnnoMethod
    public void build(@ParamType @ParamType2 String name,@ParamType2 int age){

    }

    private static Method getMethodbuild() {
        Method method;
        try {
            method = MethodEntry.class.getDeclaredMethod("build", String.class,int.class);
        } catch (NoSuchMethodException e) {
            method = null;
            e.printStackTrace();
        }
        return method;
    }

    public int getInt() throws NumberFormatException{
        return 100;
    }
    @AnnoMethod
    public Object getObj(){
        return new Object();
    }

    private static Method getMethodEntry() {
        Method method;
        try {
            method = MethodEntry.class.getDeclaredMethod("entry", String.class);
        } catch (NoSuchMethodException e) {
            method = null;
            e.printStackTrace();
        }
        return method;
    }

    private static Method getMethod(String name) {
        Method method;
        try {
            method = MethodEntry.class.getDeclaredMethod(name, new Class[0]);
        } catch (NoSuchMethodException e) {
            method = null;
            throw new Error("method " +name + "== numm");
        }
        return method;
    }

    /**
     * 如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null。
     */
    public static void getAnnotation() {
        Method method = getMethodEntry();
        // 只能获取到注解方法的，不能得到注解参数的
        AnnoMethod am = method.getAnnotation(AnnoMethod.class); // am != null
        ParamType pm = method.getAnnotation(ParamType.class);  // pm == null
        System.out.println("am :" + am);
        System.out.println("pm :" + pm);
        System.out.println("------------------------------");
    }

    /**
     * result : @com.xhgjky.framework.T.reflect.annotation.AnnoMethod()
     *
     * @com.xhgjky.framework.T.reflect.annotation.AnnoMethod2()
     */
    public static void getDeclaredAnnotations() {
        Method method = getMethodEntry();
        // 只能获取到注解方法的，不能得到注解参数的
        Annotation[] ans = method.getDeclaredAnnotations();
        for (Annotation a : ans) {
            System.out.println(a);
        }
        System.out.println("------------------------------");
    }

    /**
     * 获取方法所在类的 class文件对象
     * result： MethodEntry
     */
    public static void getDeclaringClass() {
        Method method = getMethodEntry();
        System.out.println(method.getDeclaringClass().getSimpleName());
        System.out.println("------------------------------");
    }

    public static void getDefaultValue() {
        Method getInt = getMethod("getInt");
        Method getObj = getMethod("getObj");
        Object in = getInt.getDefaultValue();
        Object ob = getObj.getDefaultValue();
        System.out.println(in);
        System.out.println(ob);
        System.out.println("------------------------------");
    }

    /**
     * 获取这个方法会抛出的异常的集合
     * result: Class<?>[]
     *       [class java.lang.NumberFormatException]
     *       []
     */
    public static void getExceptionTypes(){
        Method getInt = getMethod("getInt");
        Method getObj = getMethod("getObj");
        System.out.println(Arrays.toString(getInt.getExceptionTypes()));
        System.out.println(Arrays.toString(getObj.getExceptionTypes()));
        System.out.println("------------------------------");
    }

    /**
     * 获取这个方法会抛出的异常的集合
     * result: Type[]
     *       [class java.lang.NumberFormatException]
     *       []
     */
    public static void getGenericExceptionTypes(){
        Method getInt = getMethod("getInt");
        Method getObj = getMethod("getObj");
        System.out.println(Arrays.toString(getInt.getExceptionTypes()));
        System.out.println(Arrays.toString(getObj.getExceptionTypes()));
        System.out.println("------------------------------");
    }

    /**
     * 获取方法上的形式参数类型，并不能到的名字,按照顺序
     * result ：[class java.lang.String]
     *          []
     */
    public static void getGenericParameterTypes(){
        Method entry = getMethodEntry();
        Method getInt = getMethod("getInt");
        Type[] entryType = entry.getGenericParameterTypes();
        Type[] getIntType = getInt.getGenericParameterTypes();
        System.out.println(Arrays.toString(entryType));
        System.out.println(Arrays.toString(getIntType));
        System.out.println("------------------------------");
    }

    /**
     * result ：Type
     *        void
     *        int
     *
     */
    public static void getGenericReturnType(){
        Method entry = getMethodEntry();
        Method getInt = getMethod("getInt");
        Type entryType = entry.getGenericReturnType();
        Type getIntType = getInt.getGenericReturnType();
        System.out.println(entryType);
        System.out.println(getIntType);
        System.out.println("------------------------------");
    }

    /**
     * 获取是public 还是 private 返回是Int 类型的
     */
    public static void getModifiers(){
//     以整数形式返回此 Method 对象所表示方法的 Java 语言修饰符。
//     Modifier.isPublic()
//     应该使用 Modifier 类对修饰符进行解码。
    }

    /**
     * 获取 参数上的注解，一个参数对应一个集合，按顺序
     * result ： Annotation[][]
     * [[@com.xhgjky.framework.T.reflect.annotation.ParamType(),@com.xhgjky.framework.T.reflect.annotation.ParamType2()],
     * [@com.xhgjky.framework.T.reflect.annotation.ParamType2()]]
     */
    public static void getParameterAnnotations(){
          Method methodEntry = getMethodbuild();
          Annotation[][] ans = methodEntry.getParameterAnnotations();
          System.out.println(Arrays.deepToString(ans));
          System.out.println("------------------------------");
    }

    /**
     * result：Class<?>
     * int
     * class java.lang.Object
     */
    public static void getReturnType(){
        Method getInt = getMethod("getInt");
        Method getObj = getMethod("getObj");
        Class in = getInt.getReturnType();
        Class ob = getObj.getReturnType();
        System.out.println(in);
        System.out.println(ob);
        System.out.println("------------------------------");
        // public int com.xhgjky.framework.T.reflect.method.MethodEntry.getInt() throws java.lang.NumberFormatException
        System.out.println(getInt.toGenericString());
    }

    /**
     * 不知道干嘛的
     */
    public static void getTypeParameters(){

    }
}
