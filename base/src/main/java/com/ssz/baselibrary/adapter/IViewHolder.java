package com.ssz.baselibrary.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author : zsp
 * time : 2019 10 2019/10/31 15:22
 */
public interface IViewHolder<T> {

    /**
     * View的初始化
     */
    void initView();

    /**
     * 初始化ItemView
     */
    View createItemView(@NonNull ViewGroup viewGroup);

    /**
     * 数据绑定
     */
    void onBing(T bean, int position);
}
