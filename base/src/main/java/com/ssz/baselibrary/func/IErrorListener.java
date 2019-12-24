package com.ssz.baselibrary.func;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 14:53
 * 提供一种发起操作处的错误结果通知
 */
@FunctionalInterface
public interface IErrorListener<T> {
    void error(T e);
}
