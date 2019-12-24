package com.ssz.baselibrary.adapter.list;

import com.ssz.baselibrary.adapter.BaseHolder;

/**
 * @author : zsp
 * time : 2019 10 2019/10/30 16:19
 */
public abstract class BaseListHolder<T> extends BaseHolder<T> {

    /**
     * 整个Item点击监听
     */
    @Override
    public abstract void onClick();
}
