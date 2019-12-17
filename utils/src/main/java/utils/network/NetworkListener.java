package utils.network;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : zsp
 * time : 2019 10 2019/10/11 9:42
 */
@Target(ElementType.METHOD)//注解目标是方法
@Retention(RetentionPolicy.RUNTIME)//jvm运行时执行
public @interface NetworkListener {
    @NetType String[] type() default NetType.CONNECT;//参数默认值
}
