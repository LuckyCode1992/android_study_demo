package com.justcode.hxl.androidstudydemo.霓虹灯效果

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_demo2.*
import java.util.*

class Demo2Activity : AppCompatActivity() {



    class MyHandler(val names: MutableList<View>) : Handler() {
        var currentColor = 0
        //定义一组颜色
        internal val colors = arrayListOf<Int>(
            Color.parseColor("#ff0000"),
            Color.parseColor("#00ff00"),
            Color.parseColor("#0000ff"),
            Color.parseColor("#ffff00"),
            Color.parseColor("#ff00ff"),
            Color.parseColor("#00ffff")

        )

        override fun handleMessage(msg: Message?) {

            if (msg?.what == 999) {
                for (i in names.indices) {
                    names[i].setBackgroundColor(colors[(i + currentColor) % colors.size])
                }
                currentColor++
            }
            super.handleMessage(msg)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)
        val names = arrayListOf<View>(v1, v2, v3, v4, v5, v6)
        val handler = MyHandler(names)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(999)
            }

        }, 0, 200)

    }
}
