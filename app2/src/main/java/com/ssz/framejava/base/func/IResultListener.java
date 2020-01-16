package com.ssz.framejava.base.func;

/**
 * @author : zsp
 * time : 2020 01 2020/1/16 13:11
 */
public interface IResultListener<T,R> {

    void result(T result);

    void error(R e);
}
