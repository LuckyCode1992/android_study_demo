package com.justcode.hxl.androidstudydemo.数据存储和io

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main16.*

class Main16Activity : AppCompatActivity() {
    var timeLong by PropertyBySharedPref<Long>(default = 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        btn_setSp.setOnClickListener {
            timeLong = System.currentTimeMillis()
        }
        btn_getSp.setOnClickListener {
            tv_sp.text = timeLong.toString()
        }
        btn_Sp_clear.setOnClickListener {
            PropertyBySharedPref.clear()
        }

        btn_setFile.setOnClickListener {
            FileUtil.write(content = System.currentTimeMillis().toString(), context = this)
        }
        btn_getFile.setOnClickListener {
            tv_file.text = FileUtil.read(context = this)
        }
        btn_file_clear.setOnClickListener {
            FileUtil.delFile(context = this)
        }


    }
}
