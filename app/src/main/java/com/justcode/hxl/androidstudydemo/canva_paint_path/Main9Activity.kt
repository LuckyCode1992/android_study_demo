package com.justcode.hxl.androidstudydemo.canva_paint_path

import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main9.*

class Main9Activity : AppCompatActivity() {

    val colors = arrayListOf<Int>(Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.BLACK)
    var colorIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)
        btn_update_color.setOnClickListener {
            myview.updateColor(colors[colorIndex % 6])
            colorIndex++

        }
        btn_add_Shader.setOnClickListener {
            myview.addShader(25f, 20f, 20f, Color.GRAY)
        }
        btn_clear_Shader.setOnClickListener {
            myview.clearShader()
        }
        btn_stroke.setOnClickListener {
            myview.updateStyle(Paint.Style.STROKE)
        }
        btn_fill.setOnClickListener {
            myview.updateStyle(Paint.Style.FILL)
        }
        btn_fill_and_stroke.setOnClickListener {
            myview.updateStyle(Paint.Style.FILL_AND_STROKE)
        }


    }
}
