package com.ssz.framejava.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : zsp
 * time : 2019 10 2019/10/31 14:16
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<Holder> {

    protected final List<T> mList = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        IViewHolder<T> holder = createViewHolder(viewType);
        View view = holder.createItemView(viewGroup);
        return new Holder<>(view, holder);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final T bean = mList.get(position);
        holder.mViewHolder.onBing(bean, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /*******************************************************************************/



    /********************************************************************************/

    public int size() {
        return mList.size();
    }

    public abstract IViewHolder<T> createViewHolder(int viewType);

    public void addItem(T value) {
        mList.add(value);
    }

    public void addItem(int index, T value) {
        mList.add(index, value);
    }

    public void addItems(List<T> values) {
        mList.addAll(values);
    }

    public void removeItem(T value) {
        mList.remove(value);
    }

    public void removeItems(List<T> value) {
        mList.removeAll(value);
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public List<T> getItemsUnsafe() {
        return mList;
    }

    public List<T> getItemsSafe() {
        return Collections.unmodifiableList(mList);
    }

    public void refreshItems(List<T> list) {
        clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
    }
}
