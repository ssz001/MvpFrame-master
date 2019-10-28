package com.ssz.studydemo.view.dispatch

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.R
import kotlinx.android.synthetic.main.fragment_dispatch.*

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 15:04
 */
@SuppressLint("ValidFragment")
class DispatchFragment(private val type : String,private val drawable : Drawable) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dispatch,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v_background.background = drawable
        tv_type.text = type
    }
}