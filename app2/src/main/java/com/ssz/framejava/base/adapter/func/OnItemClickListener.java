package com.ssz.framejava.base.adapter.func;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author : zsp
 * time : 2019 10 2019/10/31 14:44
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position, @Nullable final Object bean);
}