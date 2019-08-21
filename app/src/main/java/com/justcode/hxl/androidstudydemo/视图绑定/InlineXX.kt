package com.justcode.hxl.androidstudydemo.视图绑定

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun Activity.bind2TextValue(@IdRes viewId: Int) = object : ReadWriteProperty<Any?, String> {
    val textView by lazy { findViewById<TextView>(viewId) }
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return textView.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        textView.text = value
    }

}