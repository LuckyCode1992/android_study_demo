package com.justcode.hxl.androidstudydemo.surface_view

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main15.*
import java.util.*

class Main15Activity : AppCompatActivity() {

    private var holder: SurfaceHolder? = null
    private var paint = Paint()
    val HEIGHT = 400
    var screenWidth = 0
    val X_OFFSET = 5
    var cx = X_OFFSET

    //y轴的位置
    var centerY = HEIGHT / 2

    var timer = Timer()
    var task: TimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main15)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        screenWidth = metrics.widthPixels

        holder = show.holder
        paint.color = Color.GREEN
        paint.strokeWidth = 4f
        btn_sin.setOnClickListener {
            drawBack(holder)
            cx = X_OFFSET
            if (task != null) {
                task?.cancel()
            }
            task = object : TimerTask() {
                override fun run() {
                    paint.color = Color.GREEN
                    val cy = centerY - (100 * Math.sin((cx - 5).toDouble() * 2.0 * Math.PI / 150)).toInt()
                    val canvas =
                        holder?.lockCanvas(Rect(cx, cy, cx + paint.strokeWidth.toInt(), cy + paint.strokeWidth.toInt()))
                    canvas?.drawPoint(cx.toFloat(), cy.toFloat(), paint)
                    cx += 2
                    if (cx > screenWidth) {
                        task?.cancel()
                        task = null
                    }
                    holder?.unlockCanvasAndPost(canvas)
                }
            }
            timer.schedule(task, 0, 10)
        }
        btn_cos.setOnClickListener {
            drawBack(holder)
            cx = X_OFFSET
            if (task != null) {
                task?.cancel()
            }
            task = object : TimerTask() {
                override fun run() {
                    paint.color = Color.GREEN
                    val cy = centerY - (100 * Math.cos((cx - 5).toDouble() * 2.0 * Math.PI / 150)).toInt()
                    val canvas =
                        holder?.lockCanvas(Rect(cx, cy, cx + paint.strokeWidth.toInt(), cy + paint.strokeWidth.toInt()))
                    canvas?.drawPoint(cx.toFloat(), cy.toFloat(), paint)
                    cx += 2
                    if (cx > screenWidth) {
                        task?.cancel()
                        task = null
                    }
                    holder?.unlockCanvasAndPost(canvas)
                }
            }
            timer.schedule(task, 0, 10)
        }

        holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(surfaceHolder: SurfaceHolder, format: Int, width: Int, height: Int) {
                drawBack(holder)
            }

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                timer.cancel()
            }

            override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {

            }

        })

    }

    private fun drawBack(holder: SurfaceHolder?) {
        holder?.let {
            val canvas = it.lockCanvas()
            //绘制背景
            canvas.drawColor(Color.WHITE)
            //绘制坐标轴
            paint.color = Color.BLACK
            //X轴
            canvas.drawLine(X_OFFSET.toFloat(), centerY.toFloat(), screenWidth.toFloat(), centerY.toFloat(), paint)
            //Y轴
            canvas.drawLine(X_OFFSET.toFloat(), 40f, X_OFFSET.toFloat(), HEIGHT.toFloat(), paint)

            it.unlockCanvasAndPost(canvas)
            it.lockCanvas(Rect(0, 0, 0, 0))
            it.unlockCanvasAndPost(canvas)

        }
    }
}
