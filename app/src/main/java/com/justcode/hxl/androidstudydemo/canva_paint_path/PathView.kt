package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PathView(context: Context, set: AttributeSet) : View(context, set) {

    private var phase = 0f
    private val effects = arrayOfNulls<PathEffect>(7)
    private val colors: IntArray
    private val paint = Paint()
    private val path = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        path.moveTo(0f, 0f)
        for (i in 1..40) {
            path.lineTo(i * 25f, Math.random().toFloat() * 90)
        }
        colors = intArrayOf(
            Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW
        )
        effects[0] = null
        effects[1] = CornerPathEffect(10f)
        effects[2] = DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f), phase)
        effects[3] = DiscretePathEffect(3.0f, 5.0f)
        val p = Path()
        effects[4] = PathDashPathEffect(p, 12f, phase, PathDashPathEffect.Style.ROTATE)
        effects[5] = ComposePathEffect(effects[3], effects[4])
        effects[6] = SumPathEffect(effects[4], effects[2])
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate(8f, 8f)

        for (i in effects.indices) {
            paint.pathEffect = effects[i]
            paint.color = colors[i]
            canvas.drawPath(path, paint)
            canvas.translate(0f, 90f)
        }
        canvas.translate(0f,180f)
        paint.textSize = 30f
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        canvas.drawTextOnPath("这是一段奇怪的文字哦",path,100f,10f,paint)
    }
}