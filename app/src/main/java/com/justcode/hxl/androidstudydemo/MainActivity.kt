package com.justcode.hxl.androidstudydemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    }

    fun skip(cls: Class<*>) {
        val intent = Intent()
        intent.setClass(this, cls)
        startActivity(intent)
    }
}
