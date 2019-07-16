package com.justcode.hxl.progrect0.core.syntactic_sugar

import android.view.View

inline fun View.visible() {
    this.visibility = View.VISIBLE
}

inline fun View.gone() {
    this.visibility = View.GONE
}

inline fun View.invisible() {
    this.visibility = View.INVISIBLE
}