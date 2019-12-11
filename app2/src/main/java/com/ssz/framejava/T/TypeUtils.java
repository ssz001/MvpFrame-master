package com.ssz.framejava.T;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * @author : zsp
 * time : 2019 11 2019/11/21 10:36
 */
public final class TypeUtils {

    public static <T extends Type> String getType(T type){
        if (type instanceof ParameterizedType){
            return "ParameterizedType";
        }else if (type instanceof GenericArrayType){
            return "GenericArrayType";
        }else if (type instanceof TypeVariable){
            return "TypeVariable";
        }else if (type instanceof WildcardType){
            return "WildcardType ";
        }
        return "null";
    }

}
