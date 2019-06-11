package com.justcode.hxl.androidstudydemo.动画案例

import android.animation.*
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main13.*

//定义小球大小
const val BALL_SIZE = 50f
//定义小球从屏幕上方下落到屏幕底部的总时间
const val FULL_TIME = 1000F

class Main13Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main13)
        ll_content.addView(MyAnimationView(this))

    }

    inner class MyAnimationView(context: Context) : View(context), ValueAnimator.AnimatorUpdateListener {
        val balls = arrayListOf<ShapeHolder>()

        init {
            setBackgroundColor(Color.WHITE)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            for (shapeHoder in balls) {
                //保存cavans的坐标系统
                canvas.save()
                //坐标变换，将画布坐标系统平移到shapeHolder 的 x，y 坐标处
                canvas.translate(shapeHoder.x, shapeHoder.y)
                //将shapeHolder持有的圆形绘制到canvans上
                shapeHoder.shape?.draw(canvas)
                //恢复坐标系
                canvas.restore()
            }
        }

        override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
            invalidate()
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {

            //如果触碰事件不是按下，移动事件 则返回
            if (event.action != MotionEvent.ACTION_MOVE && event.action != MotionEvent.ACTION_DOWN) {
                return false
            }
            //从事件发生点添加一个小球
            val newBall = addBall(event.x, event.y)
            //计算小球下落动画开始的y坐标
            val startY = newBall.y
            //计算小球落地时的y坐标
            val endY = height - BALL_SIZE
            //获取屏幕高度
            val h = height.toFloat()
            //计算动画持续时间 从顶点到底部总时间是FULL_TIME  计算从y点开始到底部的时间
            val eventY = event.y
            val duration = (FULL_TIME * ((h - eventY) / h)).toInt()
            //定义小球落下的动画
            //让小球的y属性从事件发生点变化到屏幕底部
            val fallAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY)
            //设置动画时间
            fallAnim.duration = duration.toLong()
            //设置动画的插值方式，加速插值
            fallAnim.interpolator = AccelerateInterpolator()
            //为动画添加监听
            fallAnim.addUpdateListener(this)
            //定义newBall对象的alpha属性，执行从1到0的动画
            val fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f)
            //设置动画时间
            fadeAnim.duration = 250
            //为fade动画添加监听
            fadeAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    //动画结束时，将该动画关联的shapeHodler删除
                    balls.remove((animation as ObjectAnimator).target)
                }
            })
            //为fade动画添加监听器
            fadeAnim.addUpdateListener(this)
            //定义一个animatorset组合动画
            val animatorSet = AnimatorSet()
            //指定在播放fade动画前，先播放fall动画
            animatorSet.play(fallAnim).before(fadeAnim)
            //开始动画
            animatorSet.start()

            return true
        }

        private fun addBall(x: Float, y: Float): ShapeHolder {
            //创建一个圆
            val circle = OvalShape()
            //设置圆的宽高
            circle.resize(BALL_SIZE, BALL_SIZE)
            //将圆包装成一个drawable对象
            val drawable = ShapeDrawable(circle)
            //创建一个shapeHolder对象爱你个
            val shapeHolder = ShapeHolder(drawable)
            //设置其x，y 坐标
            shapeHolder.x = x
            shapeHolder.y = y
            val red = (Math.random() * 255).toInt()
            val green = (Math.random() * 255).toInt()
            val blue = (Math.random() * 255).toInt()
            // 组合颜色
            val color = -0x10000000 + red shl 16 or (green / 4 shl 8)
            val paint = drawable.paint
            //将三个颜色除以4得到的商，组合颜色
            val darkColor = (-0x10000000 or (red / 4 shl 16) or (green / 4 shl 8) or blue / 4)
            //创建圆形渐变
            val gradient = RadialGradient(37.5f, 12.5f, BALL_SIZE, color, darkColor, Shader.TileMode.CLAMP)
            paint.shader = gradient
            shapeHolder.paint = paint
            balls.add(shapeHolder)
            return shapeHolder
        }

    }

    class ShapeHolder(var shape: ShapeDrawable?) {
        internal var x = 0f
        var y = 0f
        internal var color = 0
        internal var gradient: RadialGradient? = null
        var alpha = 1f
        lateinit var paint: Paint
    }

}
