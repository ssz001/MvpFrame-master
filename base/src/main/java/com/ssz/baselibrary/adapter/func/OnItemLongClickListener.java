package com.ssz.baselibrary.adapter.func;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author : zsp
 * time : 2019 10 2019/10/31 14:45
 */
public interface OnItemLongClickListener {
    boolean onItemLongClick(View view, int position, @Nullable final Object bean);
}
