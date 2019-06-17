package com.justcode.hxl.androidstudydemo.gesture

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_gesture1.*

class Gesture1Activity : AppCompatActivity() {

    //定义手势检测器
    private lateinit var detector: GestureDetector
    private lateinit var imageView: ImageView

    //图片资源
    private lateinit var bitmap: Bitmap

    //宽高
    private var width: Int = 0
    private var height: Int = 0

    //缩放比例
    private var currentScale = 1f
    //控制图片缩放的matrix对象
    private lateinit var matrix: Matrix

    override fun onTouchEvent(event: MotionEvent): Boolean {

        return detector.onTouchEvent(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture1)
        imageView = image
        matrix = Matrix()
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.b)
        width = bitmap.width
        height = bitmap.height
        imageView.setImageBitmap(bitmap)



        detector = GestureDetector(this, object : GestureDetector.OnGestureListener {
            override fun onShowPress(p0: MotionEvent?) {
                Log.d("detector_detector", "onShowPress")

            }

            override fun onSingleTapUp(p0: MotionEvent?): Boolean {
                Log.d("detector_detector", "onSingleTapUp")
                return false
            }

            override fun onDown(p0: MotionEvent?): Boolean {
                Log.d("detector_detector", "onDown")
                return false
            }

            override fun onFling(
                event1: MotionEvent?,
                event2: MotionEvent?,
                veclocityX: Float,
                veclocityY: Float
            ): Boolean {

//                //给出范围
//                var vx = if (veclocityX > 4000) 4000f else veclocityX
//                vx = if (veclocityX < -4000) -4000f else veclocityX
//                //根据手势的速度来计算缩放比例 vx>0 则放大， vx<0 则缩小图片
//                currentScale += currentScale * vx / 4000f
//                //保证currentScale ！=0
//                currentScale = if (currentScale > 0.01f) currentScale else 0.01f
//                //重置 matrix
//                matrix.reset()
//                //缩放matrix 以160f,200f为轴心
//                matrix.setScale(currentScale, currentScale, 160f, 200f)
//                val tem = imageView.drawable as BitmapDrawable
//                //根据原始位图和matrix创建新图片
//                val bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
//                imageView.setImageBitmap(bitmap2)
//
//                //如果图片未回收，则先强制回收该图片
////                if (!tem.bitmap.isRecycled) {
////                    tem.bitmap.recycle()
////                }
                return false

            }

            override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, veclocityX: Float, veclocityY: Float): Boolean {
//                Log.d("detector_detector", "onScroll")
                Log.d("detector_detector", "onScroll : veclocityX$veclocityX-veclocityY$veclocityY")
                //给出范围
                var vx = if (veclocityX > 4000) 4000f else veclocityX
                vx = if (veclocityX < -4000) -4000f else veclocityX
                //根据手势的速度来计算缩放比例 vx>0 则放大， vx<0 则缩小图片
                val scale = currentScale * vx / 4000f
                Log.d("detector_detector", "onScroll:scale$scale")
                //手势往左是正 往上是正
                currentScale -= scale
                Log.d("detector_detector", "onScroll:currentScale$currentScale")
                //保证currentScale ！=0
                currentScale = if (currentScale > 0.01f) currentScale else 0.01f
                //重置 matrix
                matrix.reset()
                //缩放matrix 以160f,200f为轴心
                matrix.setScale(currentScale, currentScale, 160f, 200f)
                val tem = imageView.drawable as BitmapDrawable
                //根据原始位图和matrix创建新图片
                val bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
                imageView.setImageBitmap(bitmap2)

                //如果图片未回收，则先强制回收该图片
//                if (!tem.bitmap.isRecycled) {
//                    tem.bitmap.recycle()
//                }
                return true
            }

            override fun onLongPress(p0: MotionEvent?) {
                Log.d("detector_detector", "onLongPress")
            }

        })


    }
}
