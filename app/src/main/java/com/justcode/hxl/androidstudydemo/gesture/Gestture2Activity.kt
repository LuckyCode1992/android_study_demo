package com.justcode.hxl.androidstudydemo.gesture

import android.Manifest
import android.app.AlertDialog
import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.widget.ArrayAdapter
import android.widget.Toast
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_gestture2.*

class Gestture2Activity : AppCompatActivity() {

    private lateinit var gesture1: Gesture
    private lateinit var gesture2: Gesture
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestture2)

        //手势颜色
        gestureView1.gestureColor = Color.WHITE
        gestureView2.gestureColor = Color.WHITE
        //手势绘制宽度
        gestureView1.gestureStrokeWidth = 4f
        gestureView2.gestureStrokeWidth = 4f
        //手势时间完成绑定事件监听
        gestureView1.addOnGesturePerformedListener { gestureOverlayView, gesture ->
            this.gesture1 = gesture
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)

        }

        gestureView2.addOnGesturePerformedListener { gestureOverlayView, gesture ->
            this.gesture2 = gesture
            val gestureLib =
                GestureLibraries.fromFile(Environment.getExternalStorageDirectory().path + "/mygestures")
            if (gestureLib.load()) {
                Toast.makeText(this, "手势文件装载成功", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "手势文件装载失败", Toast.LENGTH_LONG).show()

            }
            //识别刚才用户绘制的手势
            val prediction = gestureLib.recognize(gesture2)
            val result = arrayListOf<String>()
            for (pred in prediction) {
                //相似度 大于2.0 才输出
                if (pred.score > 2.0) {
                    result.add("与手势：${pred.name}相似度为${pred.score}")
                }
                if (result.size > 0) {
                    val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, result)
                    AlertDialog.Builder(this).setAdapter(adapter, null)
                        .setPositiveButton("确定", null).show()
                }
            }

        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 123 && grantResults != null && grantResults[0] == 0) {


            btn_save.setOnClickListener {
                val gestureLib =
                    GestureLibraries.fromFile(Environment.getExternalStorageDirectory().path + "/mygestures")
                gestureLib.addGesture(et_gesture_name.text.toString() ?: "手势1", gesture1)
                gestureLib.save()
            }
        }
    }


}
