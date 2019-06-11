package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.justcode.hxl.androidstudydemo.R

class Main12Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyView(this, R.drawable.aa))
    }

    private class MyView(context: Context, drawableRes: Int) : View(context) {

        private val WIDTH = 20
        private val HEIGHT = 20

        //记录图片包含的顶点
        private val COUNT = (WIDTH + 1) * (HEIGHT + 1)
        //数组，保存bitmap上的点的坐标
        private val verts = FloatArray(COUNT * 2)
        //数组，保存bitmap上扭曲后的点
        private val orig = FloatArray(COUNT * 2)
        private  var bitmap: Bitmap

        init {

            isFocusable = true
            bitmap = BitmapFactory.decodeResource(resources, drawableRes)
            val bitmapWidth = bitmap.width.toFloat()
            val bitmapHeight = bitmap.height.toFloat()
            var index = 0
            for (y in 0..HEIGHT) {
                val fy = bitmapHeight * y / HEIGHT

                for (x in 0..WIDTH) {
                    val fx = bitmapWidth * x / WIDTH
                    //初始化 orig verts 数组，初始化后，orig，verts
                    //两个数组均匀地保存在一个 21*21个点的坐标
                    // x坐标存放在 下标为0，2，4，6上
                    //y坐标存在在 下标为 1，3，5，7上
                    verts[index * 2 + 0] = fx
                    orig[index * 2 + 0] = verts[index * 2 + 0]
                    verts[index * 2 + 1] = fy
                    orig[index * 2 + 1] = verts[index * 2 + 1]
                    index += 1

                }
            }
            setBackgroundColor(Color.WHITE)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            //对bitmap按verts数组进行扭曲
            //从第一个点（5个参数0控制）开始
            canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {

            wrap(event.x, event.y)

            return true
        }

        /**
         * 根据触摸事件的位置计算verts数组里各元素的值
         */
        private fun wrap(x: Float, y: Float) {
            var i = 0
            while (i < COUNT * 2) {
                val dx = x - orig[i + 0]
                val dy = y - orig[i + 1]
                val dd = dx * dx + dy * dy
                //计算每个坐标与当前点（xy）的距离
                val d = Math.sqrt(dd.toDouble()).toFloat()
                //计算扭曲度，距离当前点，越远，扭曲度越小
                val pull = 8000 / (dd * d)
                //对verts数组重新赋值
                if (pull >= 1) {
                    verts[i + 0] = x
                    verts[i + 1] = y
                } else {
                    //控制各顶点向触摸事件发生点偏移
                    verts[i + 0] = orig[i + 0] + dx * pull
                    verts[i + 1] = orig[i + 1] + dy * pull
                }
                //坐标是两位表示一个坐标
                i += 2


            }
            invalidate()
        }
    }
}
