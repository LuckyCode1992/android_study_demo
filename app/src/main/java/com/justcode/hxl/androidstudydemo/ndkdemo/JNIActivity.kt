package com.justcode.hxl.androidstudydemo.ndkdemo

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        btn_pwd.setOnClickListener {
            val pwdString = et_pwd.text.toString()
            val result = jniUtil.checkPwd(pwdString)
            tv_pwd.text = "密码校验结果:$result"
        }
        btn_c2java_sum.setOnClickListener {
            jniUtil.callBackSum()
        }
        btn_c2java_sayhello.setOnClickListener {
            jniUtil.callBackSayHello()
        }
        btn_c2java_show.setOnClickListener {
//            jniUtil.c2javashow()
            this.c2javashow2()
        }
    }

    /**
     * C调用java 更新UI
     */
    external fun c2javashow2()

    fun javashow() {
        Log.d("JNI_JNI", "c调用java，更新了UI")
        tv_c2java_show.text = "c调用java，更新了UI"
    }
}
