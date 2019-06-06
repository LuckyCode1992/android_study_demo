package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyDraw(context: Context, set: AttributeSet) : View(context, set) {
    //定义记录前一个拖动事件的坐标
    private var preX = 0f
    private var preY = 0f
    private val path = Path()

    internal var paint = Paint(Paint.DITHER_FLAG)


    init {
        paint.textSize = 20f
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        //抗锯齿
        paint.isAntiAlias = true
        //防抖动
        paint.isDither = true

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(path, paint)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        //获取拖动事件的发生位置
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //从前一个点绘制到当前点之后，把当前点定义成下一次绘制的前一个点
                path.moveTo(x, y)
                preX = x
                preY = y
            }
            MotionEvent.ACTION_MOVE -> {
                //从前一个点绘制到当前点之后，把当前点定义成下一次绘制的前一个点
                path.lineTo(x, y)
                preY = x
                preY = y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }


        return true
    }

}