package com.justcode.hxl.androidstudydemo.时钟相关view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_demo4.*

class Demo4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo4)

        setbase.setOnClickListener {
            chronometer.base = 1000
        }

        start.setOnClickListener {
            chronometer.start()
        }
        stop.setOnClickListener {
            chronometer.stop()
        }
        chronometer.setOnChronometerTickListener {
            Log.d("Demo4Activity","回调了")
        }
    }
}
