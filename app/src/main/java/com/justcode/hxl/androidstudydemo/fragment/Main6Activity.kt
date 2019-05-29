package com.justcode.hxl.androidstudydemo.fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.widget.Toast
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main6.*

class Main6Activity : AppCompatActivity() {
    var isReplace = false
    var name = ""
    val fragment1 by lazy {
        val fragment = Fragment0()
        fragment
    }
    val fragment2 by lazy {
        val fragment = Fragment0()
        fragment
    }
    val fragment3 by lazy {
        val fragment = Fragment0()
        fragment
    }
    var showingFragment: BaseFragment? = null
    val arguments = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)
        rb_replace.isChecked = true
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_showhide -> {
                    isReplace = false
                    Toast.makeText(this, "show_hide模式", Toast.LENGTH_LONG).show()
                }
                R.id.rb_replace -> {
                    isReplace = true
                    Toast.makeText(this, "replace模式", Toast.LENGTH_LONG).show()
                }
            }
        }
        btn_send.setOnClickListener {
            if (TextUtils.isEmpty(id_input_ac.text.toString())) {

            } else {
                sendMessg(id_input_ac.text.toString(), name)
            }
        }

        btn1.setOnClickListener {
            name = "fragment1"
            arguments.putInt("background", R.drawable.a)
            arguments.putString("name", "fragment1")
            swichFragment(fragment1, arguments)
            registerOnfragmentCall(fragment1)

        }
        btn2.setOnClickListener {
            name = "fragment2"
            arguments.putInt("background", R.drawable.b)
            arguments.putString("name", "fragment2")
            swichFragment(fragment2, arguments)
            registerOnfragmentCall(fragment2)
        }
        btn3.setOnClickListener {
            name = "fragment3"
            arguments.putInt("background", R.drawable.c)
            arguments.putString("name", "fragment3")
            swichFragment(fragment3, arguments)
            registerOnfragmentCall(fragment3)
        }
    }

    private fun registerOnfragmentCall(fragment: BaseFragment) {
        fragment.item = {
            tv_content_backfragment.text = "${fragment.name}返回的数据：${it}"
        }

    }

    private fun swichFragment(fragment: BaseFragment, arguments: Bundle) {
        if (isReplace) {
            switchFragment_replace(fragment, arguments)
        } else {
            switchFragment_hide_show(fragment, arguments)
        }
    }

    private fun switchFragment_hide_show(fragment: BaseFragment, bundle: Bundle) {
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        showingFragment?.let {
            if (it.isAdded) {
                transaction.hide(it)
            }
        }
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.fl_content, fragment)
        }
        transaction.commitAllowingStateLoss()
        showingFragment = fragment
    }

    private fun switchFragment_replace(fragment: BaseFragment, bundle: Bundle) {
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_content, fragment)
        transaction.commitAllowingStateLoss()
        showingFragment = fragment
    }

    private fun sendMessg(content: String, name: String) {
        arguments.putString(name, content)
        showingFragment?.arguments = arguments
    }
}
