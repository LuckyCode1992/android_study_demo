package com.justcode.hxl.androidstudydemo.资源文件xml相关

import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main8.*

class Main8Activity : AppCompatActivity() {
    val handler = Handler()
    var drawable: ClipDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)

        drawable = iv_img.drawable as ClipDrawable
        Log.d("level_level", "${drawable!!.level}")
        iv_img.setOnClickListener {
            drawable!!.level = 0
            run()
        }

    }

    fun run() {
        handler.postDelayed({

            drawable!!.level = drawable!!.level + 100
            Log.d("level_level", "${drawable!!.level}")
            if (drawable!!.level >= 10000) {

                return@postDelayed
            }
            run()
        }, 100)
    }
}
