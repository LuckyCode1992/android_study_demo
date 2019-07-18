package com.justcode.hxl.androidstudydemo.多媒体应用.cameraV2

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView

class AutoFitTextureView(context: Context, attributeSet: AttributeSet) : TextureView(context, attributeSet) {
    var rationWidth = 0
    var rationHeight = 0
    fun setAspectRatio(width: Int, height: Int) {
        rationWidth = width
        rationHeight = height

        //刷新方法  onMeasure onLayout,onDraw方法会执行
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == rationWidth || 0 == rationHeight) {
            setMeasuredDimension(width, height)

        } else {
            if (width < height * rationWidth / rationHeight) {
                setMeasuredDimension(width, width * rationHeight / rationWidth)
            } else {
                setMeasuredDimension(height * rationWidth / rationHeight, height)
            }
        }
    }

}