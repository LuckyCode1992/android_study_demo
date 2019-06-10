package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import com.justcode.hxl.androidstudydemo.R
import java.lang.ref.WeakReference
import java.util.*

class Main11Activity : AppCompatActivity() {

    //屏幕宽高
    private var tableWidth = 0f
    private var tableHeight = 0f

    //球拍的垂直位置
    private var racketY = 0f
    //球拍的宽高
    private var racketWidth = 0f
    private var racketHeight = 0f
    //小球的大小
    private var ballSize = 0f
    //小球纵向的运行速度
    private var ySpeed = 15
    private var rand = Random()

    //返回一个负0.5-0.5的比率，控制小球的运行方向
    private val xyRate = rand.nextDouble() - 0.5

    //小球的横向速度
    private var xSpeed = (ySpeed.toDouble() * xyRate * 2.0).toInt()
    ///小球的坐标
    private var ballX = rand.nextInt(200) + 20f
    private var ballY = rand.nextInt(10) + 40f

    //racketX代表水平位置
    private var racketX = rand.nextInt(200).toFloat()
    //游戏结束的标志
    private var isLose = false
    private lateinit var gameView: GameView
    val game = Game()

    class Myhandler(private var gameView: WeakReference<Main11Activity>) : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123) {
                gameView.get()?.gameView?.invalidate()
            }
        }
    }

    val handler = Myhandler(WeakReference(this))
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        racketHeight = -15f
        racketWidth = 100f
        ballSize = 25f
        game.racketWidth = racketWidth
        game.racketHeight = racketHeight
        game.ballSize = ballSize
        game.racketX = racketX
        game.isLose = isLose
        game.tableWidth = tableWidth
        game.tableHeight = tableHeight
        game.ballX = ballX
        game.ballY = ballY
        game.racketY = racketY


        gameView = GameView(this, game)
        setContentView(gameView)

        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)

        tableWidth = metrics.widthPixels.toFloat()
        tableHeight = metrics.heightPixels.toFloat()
        racketY = tableHeight - 280
        game.tableWidth = tableWidth
        game.tableHeight = tableHeight
        game.racketY = racketY

        gameView.setOnTouchListener { view, event ->

            racketX = event.rawX
            game.racketX = racketX
            view.invalidate()
//            if (event.x <= tableWidth / 10) {
//                //控制挡板左移
//                if (racketX > 0) {
//                    racketX -= 10
//                    game.racketX = racketX
//                }
//            }
//            if (event.x >= tableWidth * 9 / 10) {
//                //控制挡板右移
//                if (racketX < tableWidth - racketWidth) {
//                    racketX += 10
//                    game.racketX = racketX
//                }
//            }
            return@setOnTouchListener true
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                //如果小球碰到左边框
                if (ballX < ballSize || ballX >= (tableWidth - ballSize)) {
                    xSpeed = -xSpeed
                }
                //如果小球高度超过球拍位置，且横向不在球拍范围内，游戏结束
                if (ballY >= racketY - ballSize && (ballX < racketX || ballX > racketX + racketWidth)) {
                    timer.cancel()
                    isLose = true
                    game.isLose = isLose
                    //如果小球位于球拍之内，且到达球拍位置，小球反弹
                } else if (ballY < ballSize || (ballY >= racketY - ballSize && ballX > racketX && ballX <= racketX + racketWidth)) {
                    ySpeed = -ySpeed
                }
                //小球坐标增加
                ballX += xSpeed
                ballY += ySpeed
                game.ballX = ballX
                game.ballY = ballY

                handler.sendEmptyMessage(0x123)

            }

        }, 0, 100)

    }
}


