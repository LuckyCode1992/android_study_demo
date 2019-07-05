package com.justcode.hxl.androidstudydemo.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_borad_cast.*

class BoradCastActivity : AppCompatActivity() {
    var myReceiver2: MyReceiver2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borad_cast)
        myReceiver2 = MyReceiver2()
        btn_send.setOnClickListener {
            val intent = Intent()
            //设置action
            intent.action = "com.justcode.hxl.androidstudydemo.broadcast_message"
            intent.`package` = "com.justcode.hxl.androidstudydemo"
            intent.putExtra("msg", "静态注册的接收了")
            sendBroadcast(intent)
        }
        btn_send2.setOnClickListener {
            val intent = Intent()
            //设置action
            intent.action = "com.justcode.hxl.androidstudydemo.broadcast_register"
            intent.`package` = "com.justcode.hxl.androidstudydemo"
            intent.putExtra("msg", "动态注册的接收了")
            sendBroadcast(intent)
        }
        btn_register.setOnClickListener {
            val intentFilter = IntentFilter()
            intentFilter.addAction("com.justcode.hxl.androidstudydemo.broadcast_register")
            registerReceiver(myReceiver2, intentFilter)
        }
        btn_unregister.setOnClickListener {
            unregisterReceiver(myReceiver2)
        }

        btn_send_order.setOnClickListener {
            val intent = Intent()
            intent.action= "com.justcode.hxl.androidstudydemo.broadcast_order"
            intent.`package` = "com.justcode.hxl.androidstudydemo"
            intent.putExtra("msg", "有序广播")
            sendOrderedBroadcast(intent,null)
        }

    }
}
