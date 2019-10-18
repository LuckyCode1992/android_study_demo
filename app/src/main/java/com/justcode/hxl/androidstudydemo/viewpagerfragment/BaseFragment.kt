package com.justcode.hxl.androidstudydemo.viewpagerfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : Fragment() {
    /**
     * 布局文件
     */
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = arguments
        if (layoutRes != 0) {
            return inflater.inflate(layoutRes, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}