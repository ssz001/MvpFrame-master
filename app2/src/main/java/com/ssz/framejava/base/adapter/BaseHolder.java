package com.ssz.framejava.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author : zsp
 * time : 2019 11 2019/11/11 10:51
 */
public abstract class BaseHolder<T> implements IViewHolder<T> {

    private Context mContext;
    private View rootView;

    protected abstract int getLayoutId();

    /**
     * 对 mView 的一些预操作
     */
    protected View applyView(View itemView){

        return itemView;
    }

    @Override
    public View createItemView(@NonNull ViewGroup parent) {
        this.mContext = parent.getContext();
        rootView = LayoutInflater.from(mContext).
                inflate(getLayoutId(),parent,false);
        return applyView(rootView);
    }

    /**
     * 整个Item点击监听
     */
    public abstract void onClick();

    protected Context getContext(){
        return mContext;
    }

    protected View getItemView(){
        return rootView;
    }

    protected <V extends View> V findViewById(int id){
        return (V) rootView.findViewById(id);
    }
}
