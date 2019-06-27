package com.justcode.hxl.androidstudydemo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class NorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val endTime = System.currentTimeMillis() + 20 * 1000
        Log.d("Service_", "onStartCommand")
        while (System.currentTimeMillis() < endTime) {
            synchronized(this) {
                (this as Object).wait(endTime - System.currentTimeMillis())
            }
        }
        Log.d("Service_", "耗时任务完成")
        Log.d("Service_", "NorService"+Thread.currentThread().toString())
        return super.onStartCommand(intent, flags, startId)
    }

}