package com.ssz.framejava.func;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 14:08
 * 提供一种发起操作处的正确结果通知
 */
@FunctionalInterface
public interface ISuccessListener<T> {
    void result(T result);
}
