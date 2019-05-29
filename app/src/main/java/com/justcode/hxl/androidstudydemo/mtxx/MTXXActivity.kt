package com.justcode.hxl.androidstudydemo.mtxx

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.justcode.hxl.androidstudydemo.R
import com.mt.mtxx.image.JNI
import kotlinx.android.synthetic.main.activity_mtxx.*

class MTXXActivity : AppCompatActivity() {
    private var jni: JNI? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mtxx)
        jni = JNI()
    }

    fun lomoHDR(view: View) {

        //6.1，把图片转换成数组
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.j)
        //装图片的像数
        val pixels = IntArray(bitmap.width * bitmap.height)
        /**
         * 参数
         *
         * pixels       接收位图颜色值的数组
         *
         * offset      写入到pixels[]中的第一个像素索引值
         *
         * stride       pixels[]中的行间距个数值(必须大于等于位图宽度)。可以为负数
         *
         * x             从位图中读取的第一个像素的x坐标值。
         *
         * y             从位图中读取的第一个像素的y坐标值
         *
         * width       从每一行中读取的像素宽度
         *
         * height 　　读取的行数
         *
         * 　　异常
         */
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        //6.2,把数组传入给C代码处理
        jni?.StyleLomoHDR(pixels, bitmap.width, bitmap.height)
        // 6.3，把处理好的数组重新生成图片
        bitmap = Bitmap.createBitmap(pixels, bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        // 6.4,把图片像数
        iv_icon.setImageBitmap(bitmap)


    }

    fun lomoC(view: View) {

        //6.1，把图片转换成数组
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.j)
        //装图片的像数
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        //6.2,把数组传入给C代码处理
        jni?.StyleLomoC(pixels, bitmap.width, bitmap.height)
        // 6.3，把处理好的数组重新生成图片
        bitmap = Bitmap.createBitmap(pixels, bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        // 6.4,把图片像数
        iv_icon.setImageBitmap(bitmap)

    }

    fun lomoB(view: View) {
        //6.1，把图片转换成数组
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.j)
        //装图片的像数
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        //6.2,把数组传入给C代码处理
        jni?.StyleLomoB(pixels, bitmap.width, bitmap.height)
        // 6.3，把处理好的数组重新生成图片
        bitmap = Bitmap.createBitmap(pixels, bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        // 6.4,把图片像数
        iv_icon.setImageBitmap(bitmap)
    }


    fun reset(view: View) {

        iv_icon.setImageResource(R.drawable.j)
    }
}
