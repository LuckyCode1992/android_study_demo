package com.justcode.hxl.androidstudydemo.adapterviewfipper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterViewFlipper
import android.widget.BaseAdapter
import android.widget.ImageView
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_demo5.*
import kotlin.math.abs

class Demo5Activity : AppCompatActivity() {

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
        setContentView(R.layout.activity_demo5)

        val adapter = object : BaseAdapter() {
            override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
                val imageview: ImageView
                if (view == null) {
                    imageview = ImageView(this@Demo5Activity)
                    imageview.setImageResource(imgIds[position])
                    imageview.scaleType = ImageView.ScaleType.FIT_XY
                    imageview.layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                } else {
                    imageview = view as ImageView
                }
                return imageview
            }

            override fun getItem(position: Int): Any {
                return position
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getCount(): Int {
                return imgIds.size
            }

        }

        flipper?.adapter = adapter

        btn_next.setOnClickListener {
            flipper?.showNext()
        }
        btn_pre.setOnClickListener {
            flipper?.showPrevious()
        }
        btn_auto.setOnClickListener {
            flipper?.startFlipping()
        }
        btn_stop.setOnClickListener {
            flipper?.stopFlipping()
        }

    }
}
