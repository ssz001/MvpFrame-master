package com.ssz.studydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val drawList = mutableListOf("item1","item2","item3","item4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(t_toolbar)
        t_toolbar.setNavigationOnClickListener {
//            dl_draw.openDrawer(Gravity.START)

            if(dl_draw.isDrawerOpen(Gravity.START)){
                dl_draw.closeDrawers()
            }else{
                dl_draw.openDrawer(Gravity.START)
            }

            Toast.makeText(this,"点击了菜单",Toast.LENGTH_SHORT).show()
        }



// dl_draw.openDrawer(Gravity.START)
//        val item : ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,drawList)
    }
}
