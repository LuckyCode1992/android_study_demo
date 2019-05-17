package com.justcode.hxl.androidstudydemo.stackview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SimpleAdapter
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main2.*
import android.widget.ImageView


class Main2Activity : AppCompatActivity() {
    val imgIds = intArrayOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i,
        R.drawable.j
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_next.setOnClickListener {
            stackview.showNext()
        }
        btn_pre.setOnClickListener {
            stackview.showPrevious()
        }
        stackview.adapter = object : BaseAdapter() {
            override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                val imageView: ImageView
                if (p1 == null) {
                    imageView = ImageView(this@Main2Activity)
                    imageView.setImageResource(imgIds[p0])
                    imageView.scaleType = ImageView.ScaleType.MATRIX
                    imageView.layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                } else {
                    imageView = p1 as ImageView
                }
                return imageView
            }

            override fun getItem(p0: Int): Any {
                return imgIds[p0]
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun getCount(): Int {
                return imgIds.size
            }

        }
    }
}
