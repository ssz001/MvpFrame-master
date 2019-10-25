package com.ssz.studydemo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * @author : zsp
 * time : 2019 08 2019/8/22 16:10
 */
class ContentDataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return Holder(View.inflate(p0.context, R.layout.item_list_content_data,null))
    }

    override fun getItemCount(): Int {
       return 15
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
           with(p0.itemView){

           }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}