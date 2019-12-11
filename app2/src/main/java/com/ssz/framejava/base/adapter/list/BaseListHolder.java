package com.ssz.framejava.base.adapter.list;


import com.ssz.framejava.base.adapter.BaseHolder;

/**
 * @author : zsp
 * time : 2019 10 2019/10/30 16:19
 */
public abstract class BaseListHolder<T> extends BaseHolder<T> {

    /**
     * 整个Item点击监听
     */
    public abstract void onClick();
}
