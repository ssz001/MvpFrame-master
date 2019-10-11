package com.ssz.studydemo.view.launch

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ssz.frame.base.BaseActivity
import com.ssz.frame.utils.LogUtils
import com.ssz.frame.utils.ToastUtils
import com.ssz.frame.utils.network.NetType
import com.ssz.frame.utils.network.NetworkListener
import com.ssz.frame.utils.network.NetworkManager
import com.ssz.studydemo.R
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId() = R.layout.activity_launch


    override fun initView() {
//      var lk = arrayOf(NetType.NONE,NetType.MOBILE)
    }


    override fun initData() {
//       RxViewUtils.clickView(bt_click)
//                .throttleFirst(1,TimeUnit.SECONDS)
//                .subscribe {
//                    LogUtils.d("RxViewUtils","点击- "+System.currentTimeMillis())
//                }

        with(rv_func){
            layoutManager =  LinearLayoutManager(this@LaunchActivity).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = FuncAdapter(mutableListOf("study", "book", "movie", "music")).apply {
                listener = { funName, position ->
//                    for (index in 1..1000){
//                         bt_click.performClick()
//                         Thread.sleep(10)
//                    }

//                    ToastUtils.showToast(this@LaunchActivity, funName)
//                    startActivity(Intent(this@LaunchActivity,ReadActivity::class.java))
                } }
        }
        NetworkManager.getDefault().registerNetWorkObserver(this)
    }

    @NetworkListener(type = [NetType.WIFI,NetType.MOBILE,NetType.NONE,NetType.MOBILE_4G,NetType.MOBILE_3G])
    fun netWorkListener(type : String){
        when(type){
            NetType.MOBILE ->{
                LogUtils.d("NETWORKC","mobile")
//               ToastUtils.showToast(this,"mobile")
            }
            NetType.NONE ->{
                LogUtils.d("NETWORKC","mobile_none")
                ToastUtils.showToast(this,"mobile_disconnect")
            }
            NetType.MOBILE_4G ->{
                LogUtils.d("NETWORKC","mobile_4g")
                ToastUtils.showToast(this,"mobile_4G")
            }
            NetType.WIFI ->{
                LogUtils.d("NETWORKC","mobile_wifi")
                ToastUtils.showToast(this,"mobile_wifi")
            }
            NetType.MOBILE_3G ->{
                LogUtils.d("NETWORKC","mobile_3G")
                ToastUtils.showToast(this,"mobile_3G")
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


    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getDefault().unRegisterNetWorkObserver(this)
    }
}
