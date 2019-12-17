package com.ssz.studydemo.ui.launch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.R
import kotlinx.android.synthetic.main.item_list_fun_select.view.*

/**
 * @author : zsp
 * time : 2019 09 2019/9/19 18:48
 */
class FuncAdapter(private val funList : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener : ((funName : String,position : Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
       return Holder(LayoutInflater.from(p0.context)
               .inflate(R.layout.item_list_fun_select,p0,false))
    }

    override fun getItemCount() = funList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
          val funName = funList[position]
          with(holder.itemView){
              tv_fun_name.apply { text = funName }
                      .setOnClickListener{ listener?.invoke(funName,position) }
          }
    }

    inner class Holder (itemView : View) : RecyclerView.ViewHolder(itemView)
}