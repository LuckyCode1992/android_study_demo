package com.justcode.hxl.androidstudydemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.adapterviewfipper.Demo5Activity
import com.justcode.hxl.androidstudydemo.textview相关.Demo3Activity
import com.justcode.hxl.androidstudydemo.时钟相关view.Demo4Activity
import com.justcode.hxl.androidstudydemo.跟随手指的小球.Demo1Activity
import com.justcode.hxl.androidstudydemo.霓虹灯效果.Demo2Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_demo1.setOnClickListener {
            skip(Demo1Activity::class.java)
        }
        btn_demo2.setOnClickListener {
            skip(Demo2Activity::class.java)
        }
        btn_demo3.setOnClickListener {
            skip(Demo3Activity::class.java)
        }
        btn_demo4.setOnClickListener {
            skip(Demo4Activity::class.java)
        }
        btn_demo5.setOnClickListener {
            skip(Demo5Activity::class.java)
        }
    }

    fun skip(cls: Class<*>) {
        val intent = Intent()
        intent.setClass(this, cls)
        startActivity(intent)
    }
}
