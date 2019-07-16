package com.justcode.hxl.progrect0.core.syntactic_sugar

import android.widget.Toast
import com.justcode.hxl.androidstudydemo.MyApp

inline fun String.toast() {
    Toast.makeText(MyApp.context, this, Toast.LENGTH_SHORT).show()
}