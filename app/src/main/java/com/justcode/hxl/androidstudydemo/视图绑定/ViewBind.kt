package com.justcode.hxl.androidstudydemo.视图绑定

import android.app.Activity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_coroutines.view.*

class XXActivity : Activity() {

    var name: String by bind2TextValue(R.id.tv_text)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = "hello"
    }

}

