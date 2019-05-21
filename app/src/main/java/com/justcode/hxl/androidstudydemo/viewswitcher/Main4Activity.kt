package com.justcode.hxl.androidstudydemo.viewswitcher

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main4.*

const val NUMBER_PRE_SCREEN = 12

class Main4Activity : AppCompatActivity() {

    val items = arrayListOf<DataItem>(
        DataItem("0", R.drawable.a),
        DataItem("1", R.drawable.b),
        DataItem("2", R.drawable.c),
        DataItem("3", R.drawable.d),
        DataItem("4", R.drawable.e),
        DataItem("5", R.drawable.f),
        DataItem("6", R.drawable.g),
        DataItem("7", R.drawable.h),
        DataItem("8", R.drawable.i),
        DataItem("9", R.drawable.j),
        DataItem("10", R.drawable.k),
        DataItem("11", R.drawable.m),
        DataItem("12", R.drawable.n),
        DataItem("13", R.drawable.l),
        DataItem("14", R.drawable.o),
        DataItem("15", R.drawable.p),
        DataItem("16", R.drawable.b),
        DataItem("17", R.drawable.a),
        DataItem("18", R.drawable.q),
        DataItem("19", R.drawable.q),
        DataItem("20", R.drawable.q)


    )
    //当前显示第几屏
    var screenNo = -1
    //程序总屏幕数
    var screenCount = 0
    val adapter = object : BaseAdapter() {
        override fun getView(position: Int, view0: View?, parent: ViewGroup?): View {
            var view = view0
            if (view0 == null) {
                view = LayoutInflater.from(this@Main4Activity).inflate(R.layout.labelicon, null)
            }
            val imageView = view?.findViewById<ImageView>(R.id.iv_lab)
            val textView = view?.findViewById<TextView>(R.id.tv_lab)
            getItem(position).image?.let { imageView?.setImageResource(it) }
            getItem(position).dataName?.let { textView?.text = it }

            return view ?: View(this@Main4Activity)
        }

        override fun getItem(position: Int): DataItem {
            return items[screenNo * NUMBER_PRE_SCREEN + position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            //如果已经到了最后一屏，且应用程序的数量不能整除 NUMBER_PRE_SCREEN
            return if (screenNo == screenCount - 1 && items.size % NUMBER_PRE_SCREEN != 0) {
                //最后一屏显示的程序数为应用程序的数量对 NUMBER_PRE_SCREEN 求余数
                items.size % NUMBER_PRE_SCREEN
            } else {
                //否则每屏显示 NUMBER_PRE_SCREEN
                NUMBER_PRE_SCREEN
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        btn_pre.setOnClickListener {
            pre(it)
        }
        btn_next.setOnClickListener {
            next(it)
        }

        screenCount = if (items.size % NUMBER_PRE_SCREEN == 0) {
            items.size / NUMBER_PRE_SCREEN
        } else {
            items.size / NUMBER_PRE_SCREEN + 1
        }
        viewswitcher.setFactory(ViewSwitcher.ViewFactory {
            LayoutInflater.from(this).inflate(R.layout.slidelistview, null)
        })
        next(null)


    }

    private fun next(view: View?) {
        if (screenNo < screenCount - 1) {
            screenNo++
            viewswitcher.setInAnimation(this, R.anim.slide_in_right)
            viewswitcher.setOutAnimation(this, R.anim.slide_out_left)

            (viewswitcher.nextView as GridView).adapter = adapter
            viewswitcher.showNext()

        }
    }
    fun pre(view: View?){
        if (screenNo>0){
            screenNo--
            viewswitcher.setInAnimation(this, R.anim.slide_in_left)
            viewswitcher.setOutAnimation(this, R.anim.slide_out_right)
            (viewswitcher.nextView as GridView).adapter = adapter
            viewswitcher.showPrevious()
        }
    }
}

data class DataItem(var dataName: String? = null, var image: Int? = null)
