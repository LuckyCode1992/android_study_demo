package com.justcode.hxl.androidstudydemo.跟随手指的小球

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var currentX = 40f
    var currentY = 40f
    //定义并创建画笔
    val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置画笔颜色
        paint.color = Color.RED
        //绘制一个球
        canvas?.drawCircle(currentX,currentY,15f,paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //修改currentX，currentY两个属性
        event?.let {
            currentX = it.x
            currentY = it.y
            //通知当前组件重绘
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }
}