package com.ssz.studydemo.view.launch

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ssz.frame.base.BaseActivity
import com.ssz.frame.utils.ToastUtils
import com.ssz.studydemo.R
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId() = R.layout.activity_launch


    override fun initView() {

    }

    override fun initData() {
        rv_func.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.VERTICAL }
        rv_func.adapter = FuncAdapter(mutableListOf("study", "book", "movie", "music")).also {
            it.listener = {funName, position ->
                ToastUtils.showToast(this,funName)
            }
        }
    }

    override fun setEvent() {}

    override fun onClick(v: View?) {
//        LogUtils.d("onc","onclick --")
//        when(v?.id){
//            R.id.rv_func -> Toast.makeText(this,"点击了RecycleView",Toast.LENGTH_LONG).show()
//        }
    }
}
