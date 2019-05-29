package com.justcode.hxl.androidstudydemo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.fragment.*

class Fragment0 : BaseFragment() {


    override val layoutRes: Int
        get() = R.layout.fragment


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val name =  arguments?.getString("name")
        tv_name.text = "名字：${name}"
        val content = arguments?.getString(name)
        tv_content.text = "activity传过来的数据${content}"
        ll.setBackgroundResource(background)
        btn_get.setOnClickListener {
            val content = arguments?.getString(name)
            content?.let {
                tv_content.text = "activity传过来的数据${content}"
            }
        }
        btn_send.setOnClickListener {
            if (TextUtils.isEmpty(et_input.text.toString())) {

            } else {
                item.invoke(et_input.text.toString())
            }
        }
    }


}