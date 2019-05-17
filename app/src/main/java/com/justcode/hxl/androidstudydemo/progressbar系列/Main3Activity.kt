package com.justcode.hxl.androidstudydemo.progressbar系列

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        secbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    iv.imageAlpha = (p1.toFloat()/100*255).toInt()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            }
        )
        ratingbar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            iv.imageAlpha = (fl.toFloat()/5*255).toInt()
        }
    }
}
