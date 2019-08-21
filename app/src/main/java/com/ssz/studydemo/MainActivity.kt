package com.ssz.studydemo

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(t_toolbar)
        t_toolbar.setNavigationOnClickListener {
            if(dl_draw.isDrawerOpen(Gravity.START)){
                dl_draw.closeDrawers()
            }else{
                dl_draw.openDrawer(Gravity.START)
            }
            Toast.makeText(this,"点击了菜单",Toast.LENGTH_SHORT).show()
        }
        init()
    }

    fun init(){
        nav_view.setNavigationItemSelectedListener (this)
        float_button.setOnClickListener(this)
        val headView = View.inflate(this,R.layout.nav_head_view,null)
        nav_view.addHeaderView(headView)
        headView.findViewById<ImageView>(R.id.iv_head).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.float_button -> {
              val snackbar = Snackbar.make(cl_layout,"点击了FloatActionButton",Snackbar.LENGTH_SHORT)
               snackbar.setAction("撤销") {
                   snackbar.dismiss()
               }
               snackbar.show()
           }
           R.id.iv_head -> {
               Toast.makeText(this,"点击了头像",Toast.LENGTH_SHORT).show()
           }
       }
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.nav_home -> Log.d(TAG,"home")
            R.id.nav_item2 -> Log.d(TAG,"item2")
            R.id.nav_item3 -> Log.d(TAG,"item3")
            R.id.nav_item4 -> Log.d(TAG,"item4")
            R.id.nav_item5 -> Log.d(TAG,"item5")
        }
        return true
    }
}
