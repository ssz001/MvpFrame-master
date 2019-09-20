package com.ssz.studydemo.view.launch

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ssz.frame.base.BaseActivity
import com.ssz.frame.utils.ToastUtils
import com.ssz.studydemo.R
import com.ssz.studydemo.view.read.ReadActivity
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId() = R.layout.activity_launch


    override fun initView() {

    }

    override fun initData() {
        with(rv_func){
            layoutManager =  LinearLayoutManager(this@LaunchActivity).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = FuncAdapter(mutableListOf("study", "book", "movie", "music")).apply {
                listener = { funName, position ->
                    ToastUtils.showToast(this@LaunchActivity, funName)
                    startActivity(Intent(this@LaunchActivity,ReadActivity::class.java))
                } }
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
