package com.ssz.framejava.base.adapter.select;


import com.ssz.framejava.base.adapter.BaseAdapter;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 14:05
 */
public abstract class BaseSelectAdapter<T extends ISelectBean> extends BaseAdapter<T> {

   private void select(boolean isAll){
       for (T value : mList){
           value.setCheck(isAll);
       }
   }

   public void selectAll(){
       select(true);
       notifyDataSetChanged();
   }

   public void unSelectAll(){
       select(false);
       notifyDataSetChanged();
   }

   public void select(int index,boolean isCheck){
       mList.get(index).setCheck(isCheck);
   }

   public void select(T value,boolean isCheck){
       for (T item : mList){
           if (item.equals(value)){
               item.setCheck(isCheck);
               break;
           }
       }
   }
}
