package com.justcode.hxl.androidstudydemo.surface_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.justcode.hxl.androidstudydemo.R
import java.util.*

class FishView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private var updateThread: UpdateViewThread? = null
    private var hasSurface = false
    private var back: Bitmap
    private val fishs = arrayListOf<Bitmap>()
    //记录绘制第几条鱼
    private var fishIndex = 0
    //定义鱼的初始位置
    private var initX = 0f
    private var initY = 500f
    //记录鱼当前位置
    private var fishX = 0f
    private var fishY = initY
    //鱼的速度
    private val fishSpeed = 12f
    //鱼移动的角度
    private var fishAngle = Random().nextInt(60)
    internal var matrix = Matrix()

    init {
        //获取surface对应的surfaceholder并将其的实例作为其callback
        holder.addCallback(this)
        //初始化背景
        back = BitmapFactory.decodeResource(context.resources, R.drawable.fish_back)
        //初始化10条鱼
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish1))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish2))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish3))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish4))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish5))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish6))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish7))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish8))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish9))
        fishs.add(BitmapFactory.decodeResource(context.resources, R.drawable.fish10))
    }

    private fun resume() {
        //创建和启动图像的更新线程
        if (updateThread == null) {
            updateThread = UpdateViewThread()
            if (hasSurface) {
                updateThread?.start()
            }
        }
    }

    private fun pause() {
        //停止图像更新线程
        if (updateThread != null) {
            updateThread?.requestExitAndWait()
            updateThread = null
        }
    }

    //surface改变时
    override fun surfaceChanged(holder: SurfaceHolder, fromat: Int, w: Int, h: Int) {
        if (updateThread!=null){
            updateThread?.onWindowResize(w,h)
        }
    }

    //surface销毁是回掉
    override fun surfaceDestroyed(holder: SurfaceHolder) {

        hasSurface = false
        pause()
    }

    //surface被创建时回掉此方法
    override fun surfaceCreated(holder: SurfaceHolder) {

        initX = width.toFloat()+50
        fishX = initX
        hasSurface =true
        resume()
    }

    internal inner class UpdateViewThread : Thread() {
        //定义一个记录图像是否更新完成的旗标
        private var done = false

        override fun run() {
            val surfaceHolder = holder
            //重复回图循环，直到线程停止
            while (!done) {
                //锁定surface，并返回要绘图的canvas
                val canvas = surfaceHolder.lockCanvas()
                //绘制背景
//                canvas.drawBitmap(back, 0f, 0f, null)
                //如果鱼游出屏幕，就初始化鱼的位置
                if (fishX < -100) {
                    fishX = initX
                    fishY = initY
                    fishAngle = Random().nextInt(60)
                }
                if (fishY < -100) {
                    fishX = initX
                    fishY = initY
                    fishAngle = Random().nextInt(60)
                }
                //使用matrix来控制鱼的旋转角度和位置
                matrix.reset()
                matrix.setRotate(fishAngle.toFloat())
                fishX -= (fishSpeed * Math.cos(Math.toRadians(fishAngle.toDouble()))).toFloat()
                fishY -= (fishSpeed * Math.sin(Math.toRadians(fishAngle.toDouble()))).toFloat()
                matrix.postTranslate(fishX, fishY)
                canvas.drawBitmap(fishs[fishIndex++ % fishs.size], matrix, null)
                //解锁canvas，渲染当前图像
                surfaceHolder.unlockCanvasAndPost(canvas)
                Thread.sleep(300)
            }
        }

        fun requestExitAndWait() {
            //把这个线程标记为完成,并合并到主线程
            done = true
            join()
        }

        fun onWindowResize(w: Int, h: Int) {
            //处理surface的大小改变事件
            Log.d("surface_surface", "w=${w}---h=${h}")
        }
    }

}