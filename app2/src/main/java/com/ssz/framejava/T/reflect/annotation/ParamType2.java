package com.ssz.framejava.T.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : zsp
 * time : 2019 11 2019/11/21 12:44
 */
@Target(ElementType.PARAMETER) //方法参数
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamType2 {
}
