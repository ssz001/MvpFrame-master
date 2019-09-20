package com.ssz.studydemo.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import com.ssz.frame.mvp.MvpActivity
import com.ssz.frame.utils.LogUtils
import com.ssz.frame.utils.StatusBarUtils
import com.ssz.studydemo.R
import com.ssz.studydemo.view.home.IHomeContract
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : MvpActivity(), IHomeContract.IHomeView ,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    companion object {
        private const val TAG = "HomeActivity"
    }

    override lateinit var mPresenter: IHomeContract.IHomePresenter
    override fun getLayoutId() = R.layout.activity_main
    override fun attachPresenter() = HomePresenter(this)

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.float_button -> {
//                val snackBar = Snackbar.make(cl_layout, "点击了FloatActionButton", Snackbar.LENGTH_SHORT)
//                snackBar.setAction("撤销") { snackBar.dismiss() }
//                snackBar.show()
//            }
            R.id.iv_head -> Toast.makeText(this, "点击了头像", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_home ->  LogUtils.d(TAG,"home")
            R.id.nav_item2 -> LogUtils.d(TAG,"item2")
            R.id.nav_item3 -> LogUtils.d(TAG,"item3")
            R.id.nav_item4 -> LogUtils.d(TAG,"item4")
            R.id.nav_item5 -> LogUtils.d(TAG,"item5")
        }
        return true
    }

    override fun afterOnCreate(savedInstanceState : Bundle?) {
//      Context
        setContentView(R.layout.activity_main)
//      setSupportActionBar(t_toolbar)
        setSupportActionBar(t_toolbar.also {title= ""})
        StatusBarUtils.setColorNoTranslucentForDrawerLayout(this,dl_draw,getColor(R.color.white_ffffff))

        t_toolbar.setNavigationOnClickListener {
//            if(dl_draw.isDrawerOpen(Gravity.START)) dl_draw.closeDrawers() else dl_draw.openDrawer(Gravity.START)
            dl_draw.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    dl_draw.viewTreeObserver.removeOnPreDrawListener(this)
                    dl_draw.openDrawer(Gravity.START,true)
                    return false
                }
            })
            Toast.makeText(this,"点击了菜单",Toast.LENGTH_SHORT).show()
        }
        // floatActionButton
//        float_button.setOnClickListener(this)

//        nav_view.setNavigationItemSelectedListener (this)
//        val headView = View.inflate(this, R.layout.nav_head_view,null)
//        nav_view.addHeaderView(headView)
//        headView.findViewById<ImageView>(R.id.iv_head).setOnClickListener(this)
//        sf_layout.setOnRefreshListener{ sf_layout.isRefreshing = false }
//        rv_content.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.VERTICAL }
//        rv_content.adapter = ContentDataAdapter()
    }

    override fun onDestroy() {
        mPresenter.detach()
        super.onDestroy()
    }
}
