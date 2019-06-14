package com.justcode.hxl.androidstudydemo.数据存储和io

import android.content.Context
import android.content.SharedPreferences
import com.justcode.hxl.androidstudydemo.MyApp
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PropertyBySharedPref<T>(private val keyName: String = "", private val default: T) :
    ReadWriteProperty<Any, T> {
    companion object {
        val spName: String = "androidstudydemo_name"
        private val sharedPreferences: SharedPreferences =
            MyApp.context!!.getSharedPreferences(spName, Context.MODE_PRIVATE)

        fun clear() {
            val edit = sharedPreferences.edit()
            edit.clear()
            edit.apply()
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val name = if (keyName.isEmpty()) property.name else keyName
        return with(sharedPreferences) {
            when (default) {
                is String -> getString(name, default) as T
                is Int -> getInt(name, default) as T
                is Float -> getFloat(name, default) as T
                is Boolean -> getBoolean(name, default) as T
                is Long -> getLong(name, default) as T
                else -> throw java.lang.IllegalArgumentException("not support type")
            }
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val name = if (keyName.isEmpty()) property.name else keyName
        val editor = sharedPreferences.edit()
        with(editor) {
            when (value) {
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                else -> throw IllegalArgumentException("not support type")
            }
        }
        //apply 是后台操作，不会阻塞UI线程
        editor.apply()
        //editor.commit() commit 是同步操作，会阻塞UI线程

    }
}