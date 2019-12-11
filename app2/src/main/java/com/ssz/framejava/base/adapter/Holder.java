package com.ssz.framejava.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author : zsp
 * time : 2019 10 2019/10/30 8:45
 * 中转Holder
 */
public final class Holder<T> extends RecyclerView.ViewHolder {

    public IViewHolder<T> mViewHolder;

    public Holder(@NonNull View itemView, @NonNull IViewHolder<T> viewHolder) {
        super(itemView);
        this.mViewHolder = viewHolder;
    }
}
