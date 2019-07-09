package com.justcode.hxl.androidstudydemo.多媒体应用


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_music.*


class MusicActivity : AppCompatActivity() {
    private val FILE_SELECT_CODE = 155
    var contentUtil: ContentUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentUtil = ContentUtil(this)
        setContentView(R.layout.activity_music)
        btn_get_music.setOnClickListener {
            contentUtil?.getAllMusic {

            }
        }
    }


}
