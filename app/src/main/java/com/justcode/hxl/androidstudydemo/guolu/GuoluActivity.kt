package com.justcode.hxl.androidstudydemo.guolu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R

class GuoluActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guolu)

        val pressureView = PressureView(this)



//        pressureView.setPressure()


    }

}
