package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyView(context: Context, set: AttributeSet) : View(context, set) {
    private val paht1 = Path()
    private val paht2 = Path()
    private val mShader =
        LinearGradient(0f, 0f, 40f, 60f, intArrayOf(Color.RED, Color.GREEN, Color.YELLOW), null, Shader.TileMode.REPEAT)
    private val rect = RectF()

    //定义画笔
    private val paint = Paint()
    private var style: Paint.Style = Paint.Style.STROKE
    private var color: Int = Color.BLUE

    fun updateStyle(style: Paint.Style = Paint.Style.FILL_AND_STROKE) {
        this.style = style
        invalidate()
    }

    fun updateColor(color: Int = Color.BLUE) {
        this.color = color
        invalidate()
    }

    fun addShader(
        radius: Float = 0f,
        dx: Float = 0f,
        dy: Float = 0f,
        shadowColor: Int = 0
    ) {
        if (radius != 0f && dx != 0f && dy != 0f && shadowColor != 0) {
            paint.shader = mShader
            paint.setShadowLayer(radius, dx, dy, shadowColor)

            invalidate()
        }
    }

    fun clearShader() {
        paint.shader = null
        paint.clearShadowLayer()

        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画布绘制为白色
        canvas.drawColor(Color.WHITE)
        //抗锯齿
        paint.isAntiAlias = true
        paint.color = color
        paint.style = style
        paint.strokeWidth = 4f
        val viewWidth = this.width.toFloat()

        //绘制圆形
        canvas.drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10, viewWidth / 10, paint)

        //绘制正方形
        canvas.drawRect(10f, viewWidth / 5 + 20f, viewWidth / 5 + 10f, viewWidth * 2 / 5 + 20, paint)

        //绘制矩形
        canvas.drawRect(10f, viewWidth * 2 / 5 + 30, viewWidth / 5 + 10, viewWidth / 2 + 30, paint)

        //绘制圆角矩形
        rect.set(10f, viewWidth / 2 + 40, viewWidth / 5 + 10, viewWidth * 3 / 5 + 40)
        canvas.drawRoundRect(rect, 15f, 30f, paint)

        //绘制椭圆
        rect.set(10f, viewWidth * 3 / 5 + 50, 10 + viewWidth / 5, viewWidth * 7 / 10 + 50)
        canvas.drawOval(rect, paint)

        //绘制三角形
        paht1.moveTo(10f, width * 9 / 10 + 60f)
        paht1.lineTo(viewWidth / 5 + 10, viewWidth * 9 / 10 + 60)
        paht1.lineTo(viewWidth / 10 + 10, viewWidth * 7 / 10 + 60)
        paht1.close()
        canvas.drawPath(paht1, paint)

        //绘制五角形
        paht2.moveTo(10 + viewWidth / 15, viewWidth * 9 / 10 + 70)
        paht2.lineTo(10 + viewWidth * 2 / 15, viewWidth * 9 / 10 + 70)
        paht2.lineTo(10 + viewWidth / 5, viewWidth + 70)
        paht2.lineTo(10 + viewWidth / 10, viewWidth * 11 / 10 + 70)
        paht2.lineTo(10f, viewWidth + 70)
        paht2.close()
        canvas.drawPath(paht2, paint)

        //绘制文字
        paint.textSize = 48f
        canvas.drawText("则是一段文字", 60 + viewWidth * 3 / 6, viewWidth / 10 + 10, paint)

    }

}