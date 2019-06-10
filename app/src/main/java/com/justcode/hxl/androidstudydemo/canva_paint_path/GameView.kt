package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.content.Context
import android.graphics.*
import android.view.View

class GameView(context: Context, var game: Game) : View(context) {
    private var paint = Paint()
    private var mShader =
        RadialGradient(
            -game.ballSize / 2,
            -game.ballSize / 2,
            game.ballSize,
            Color.WHITE,
            Color.RED,
            Shader.TileMode.CLAMP
        )

    init {
        isFocusable = true
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (game.isLose) {
            paint.color = Color.RED
            paint.textSize = 40f
            canvas.drawText("小游戏已经结束", game.tableWidth / 2 - 100, 200f, paint)
        } else {
            //保存坐标系统
            canvas.save()
            canvas.translate(game.ballX, game.ballY)
            //设置渐变 ，绘制小球
            paint.shader = mShader
            canvas.drawCircle(0f, 0f, game.ballSize, paint)
            //恢复原来的坐标系统
            canvas.restore()
            //设置颜色，并绘制球拍
            paint.color = Color.rgb(80, 80, 200)
            canvas.drawRect(
                game.racketX,
                game.racketY,
                game.racketX + game.racketWidth,
                game.racketY + game.racketHeight,
                paint
            )
        }

    }
}

data class Game(
    var ballSize: Float = 0f,
    var isLose: Boolean = false,
    var tableWidth: Float = 0f,
    var tableHeight: Float = 0f,
    var ballX: Float = 0f,
    var ballY: Float = 0f,
    var racketX: Float = 0f,
    var racketY: Float = 0f,
    var racketWidth: Float = 0f,
    var racketHeight: Float = 0f
)