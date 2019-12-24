package com.ssz.baselibrary.adapter.list;

import android.support.annotation.NonNull;
import android.view.View;

import com.ssz.baselibrary.adapter.Holder;
import com.ssz.baselibrary.adapter.BaseAdapter;
import com.ssz.baselibrary.adapter.func.OnItemClickListener;
import com.ssz.baselibrary.adapter.func.OnItemLongClickListener;


/**
 * @author : zsp
 * time : 2019 10 2019/10/30 8:39
 * 1.类似于ListView
 */
public abstract class BaseListAdapter<T> extends BaseAdapter<T> {

    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (!BaseListHolder.class.isInstance(holder.mViewHolder))
            throw new IllegalStateException("ERROR: mViewHolder != BaseListHolder");

        final T bean = mList.get(position);
        final BaseListHolder mViewHolder = (BaseListHolder)holder.mViewHolder;
        //设置点击事件
        holder.itemView.setOnClickListener((v) -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position, bean);
            }
            //adapter监听点击事件
            mViewHolder.onClick();
            onItemClick(v, position);
        });
        //设置长点击事件
        holder.itemView.setOnLongClickListener((v) -> {
                    boolean isClicked = false;
                    if (mItemLongClickListener != null) {
                        isClicked = mItemLongClickListener.onItemLongClick(v, position, bean);
                    }
                    //Adapter监听长点击事件
                    onItemLongClick(v, position);
                    return isClicked;
                });
    }

    protected void onItemClick(View view, int position) {}

    protected void onItemLongClick(View view, int position) {}

    /****************************** 常用操作 ***********************************/

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mItemClickListener = mListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mListener){
        this.mItemLongClickListener = mListener;
    }
}
