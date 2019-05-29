package com.justcode.hxl.androidstudydemo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    var name: String = ""
    var background: Int = 0
    var item: (String) -> Unit = {}
    /**
     * 布局文件
     */
    abstract val layoutRes: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        name = arguments?.getString("name", "没有名字") ?: "没有名字"
        background = arguments?.getInt("background", 0) ?: 0
        Log.d("BaseFragment_TAG", "${name}:onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BaseFragment_TAG", "${name}:onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("BaseFragment_TAG", "${name}:onCreateView")
        if (layoutRes != 0) {
            return inflater.inflate(layoutRes, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("BaseFragment_TAG", "${name}:onActivityCreated")
    }


    override fun onStart() {
        super.onStart()
        Log.d("BaseFragment_TAG", "${name}:onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("BaseFragment_TAG", "${name}:onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("BaseFragment_TAG", "${name}:onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BaseFragment_TAG", "${name}:onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BaseFragment_TAG", "${name}:onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BaseFragment_TAG", "${name}:onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("BaseFragment_TAG", "${name}:onDetach")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("BaseFragment_TAG", "${name}:onHiddenChanged:${hidden}")
    }

}