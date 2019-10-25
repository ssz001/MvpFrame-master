package com.ssz.studydemo.view.dispatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.R

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 15:04
 */
class DispatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dispatch,null)
        return view
    }
}