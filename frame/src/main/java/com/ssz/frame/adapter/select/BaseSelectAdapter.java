package com.ssz.frame.adapter.select;

import android.support.annotation.NonNull;

import com.ssz.frame.adapter.BaseAdapter;
import com.ssz.frame.adapter.Holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : zsp
 * time : 2019 11 2019/11/1 13:23
 */
public abstract class BaseSelectAdapter<T> extends BaseAdapter<Select<T>> {

    /***************************** 方法重写区域 ******************************/
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder,position);
    }

    /***************************** 新增方法区域 ******************************/

    /**
     * 取反设置选中状态
     */
    public boolean negateSelect(int index){
        if (index < 0 || index >= size())return false;
        Select<T> select = mList.get(index);
        select.isCheck = !select.isCheck;
        return true;
    }

    /**
     * 获取是否是选中的
     */
    public boolean isCheck(int index){
        if (index < 0 || index >= size())throw new IndexOutOfBoundsException("size <= index");
        return mList.get(index).isCheck;
    }

    /**
     * 执行单选
     */
    public void radioSelect(int index){
        unSelectAll();
        select(index,true);
    }

    /**
     * 更改某一个的状态
     */
    public void select(int index,boolean isCheck){
        if (index < 0 || index >= size())return;
        getItem(index).isCheck = isCheck;
        notifyDataSetChanged();
    }

    /**
     * 全选
     */
    public void selectAll(){
        select(true);
        notifyDataSetChanged();
    }

    /**
     * 单选
     */
    public void unSelectAll(){
        select(false);
        notifyDataSetChanged();
    }

    /**
     * 获取选中的按钮集合
     */
    public List<T> getSelectItems() {
        List<T> target = new ArrayList<>();
        for (Select<T> item : mList) {
           target.add(item.data);
        }
        return target;
    }

    public T getSelectItem() {
        T value = null;
        for (Select<T> item : mList) {
            if (item.isCheck) {
                value = item.data;
            }
        }
        return value;
    }

    private void select(boolean isCheck) {
        for (Select<T> item : mList) {
            item.isCheck = isCheck;
        }
    }

    public void addSingle(T value){
        addItem(new Select<>(value));
    }

    public void addSingle(int index,T value){
        addItem(index,new Select<>(value));
    }

    public void addList(List<T> values) {
        final List<Select<T>> addList = new ArrayList<>();
        for (final T t : values){
            addList.add(new Select<>(t));
        }
        addItems(addList);
    }

    public void removeSingle(T value) {
        Select<T> v = null;
        for (int i = 0 ; i < mList.size();i++){
           v = mList.get(i);
           if (v.equalsData(value)){
              break;
           }
        }
       removeItem(v);
    }

    public void removeList(List<T> values) {
        Select<T> s;
        List<Select<T>> removeList = new ArrayList<>();
        for (T v : values){
            for (int i = 0 ;i < mList.size();i++){
                s = mList.get(i);
                if (s.equalsData(v))removeList.add(s);
            }
        }
        removeItems(removeList);
    }

    public T getSingle(int position) {
        Select<T> item  = this.getItem(position);
        return item.data;
    }

    public List<T> getListUnsafe() {
        List<T> list = new ArrayList<>();
        for (Select<T> s : this.mList){
            list.add(s.data);
        }
        return list;
    }

    public List<T> getListSafe() {
        return Collections.unmodifiableList(getListUnsafe());
    }

}
