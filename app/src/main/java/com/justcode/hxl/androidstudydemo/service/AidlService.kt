package com.justcode.hxl.androidstudydemo.service

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import java.util.*

class AidlService : Service() {


    lateinit var catBinder: CatBinder
    var colors = arrayListOf<String>("红色", "黄色", "黑色")
    var timer = Timer()
    var wights = arrayListOf<Double>(2.3, 3.1, 1.58)
    lateinit var color: String
    var wight: Double = 0.0

    inner class CatBinder : IMyAidlInterface.Stub() {
        override fun getColor(): String {
            return this@AidlService.color
        }

        override fun getWight(): Double {
            return this@AidlService.wight
        }

    }

    override fun onCreate() {
        super.onCreate()
        catBinder = CatBinder()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val rand = (Math.random() % 3).toInt()
                color = colors[rand]
                wight = wights[rand]
            }

        }, 0, 800)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        /**
         * 返回catBinder对象，
         * 在绑定本地service的情况下，该catBinder对象会直接
         * 传递给客户端的serviceConnection对象
         * 的onServiceConnected的第二参数
         * 在绑定远程service的情况下，只是将catBinder对象的代理
         * 传给客户端的onServiceConnected的第二个参数
         */

        return catBinder
    }
}