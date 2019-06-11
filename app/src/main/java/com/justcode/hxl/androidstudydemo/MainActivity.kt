package com.justcode.hxl.androidstudydemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.androidstudydemo.adapterviewfipper.Demo5Activity
import com.justcode.hxl.androidstudydemo.calendarview.Main5Activity
import com.justcode.hxl.androidstudydemo.canva_paint_path.Main10Activity
import com.justcode.hxl.androidstudydemo.canva_paint_path.Main11Activity
import com.justcode.hxl.androidstudydemo.canva_paint_path.Main12Activity
import com.justcode.hxl.androidstudydemo.canva_paint_path.Main9Activity
import com.justcode.hxl.androidstudydemo.fragment.Main6Activity
import com.justcode.hxl.androidstudydemo.intent的部分功能.Main7Activity
import com.justcode.hxl.androidstudydemo.mtxx.MTXXActivity
import com.justcode.hxl.androidstudydemo.ndkdemo.JNIActivity
import com.justcode.hxl.androidstudydemo.progressbar系列.Main3Activity
import com.justcode.hxl.androidstudydemo.stackview.Main2Activity
import com.justcode.hxl.androidstudydemo.textview相关.Demo3Activity
import com.justcode.hxl.androidstudydemo.viewswitcher.Main4Activity
import com.justcode.hxl.androidstudydemo.动画案例.Main13Activity
import com.justcode.hxl.androidstudydemo.时钟相关view.Demo4Activity
import com.justcode.hxl.androidstudydemo.资源文件xml相关.Main8Activity
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
        btn_demo6.setOnClickListener {
            skip(Main2Activity::class.java)
        }

        btn_demo7.setOnClickListener {
            skip(Main3Activity::class.java)
        }
        btn_demo8.setOnClickListener {
            skip(Main4Activity::class.java)
        }
        btn_demo9.setOnClickListener {
            skip(Main5Activity::class.java)
        }
        btn_demo10.setOnClickListener {
            skip(Main6Activity::class.java)
        }

        btn_demo11.setOnClickListener {
            skip(Main7Activity::class.java)
        }

        btn_demo12.setOnClickListener {
            skip(Main8Activity::class.java)
        }
        btn_demo13.setOnClickListener {
            skip(Main9Activity::class.java)
        }
        btn_demo14.setOnClickListener {
            skip(Main10Activity::class.java)
        }
        btn_demo15.setOnClickListener {
            skip(Main11Activity::class.java)
        }
        btn_demo16.setOnClickListener {
            skip(Main12Activity::class.java)
        }
        btn_demo17.setOnClickListener {
            skip(Main13Activity::class.java)
        }


        btn_jni.setOnClickListener {
            skip(JNIActivity::class.java)
        }
        btn_mtxx.setOnClickListener {
            Toast.makeText(this,"目前因为使用了自己的C内容，会生成多种cpu架构的so，而mtxx只有一种放弃使用的so，这里会找不到，但是，单独使用没有问题",Toast.LENGTH_LONG).show()
            return@setOnClickListener
            skip(MTXXActivity::class.java)
        }
    }

    fun skip(cls: Class<*>) {
        val intent = Intent()
        intent.setClass(this, cls)
        startActivity(intent)
    }
}
