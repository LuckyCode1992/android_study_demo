package com.justcode.hxl.androidstudydemo.ndkdemo

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_jni.*

class JNIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        val jniUtil = JNIUtil()

        tv_sayhello.text = jniUtil.sayHello()

        btn_sum.setOnClickListener {
            val sum = jniUtil.sum(et_x.text.toString().toInt(), et_y.text.toString().toInt())
            tv_sum.text = "x+y=${sum}"
        }
        btn_stradd.setOnClickListener {
            val addstr = jniUtil.addstr("I am java ")
            tv_stringadd.text = addstr
        }
        btn_array.setOnClickListener {
            val intArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            val increaseArrayEles = jniUtil.increaseArrayEles(intArray)
            tv_array.text = increaseArrayEles.contentToString()
        }
    }
}
