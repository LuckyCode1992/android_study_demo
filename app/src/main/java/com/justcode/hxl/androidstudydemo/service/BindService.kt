package com.justcode.hxl.androidstudydemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BindService : Service() {

    private var count: Int = 0
    private var quit: Boolean = false
    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        val count: Int
            get() = this@BindService.count
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("BindService_","onBind")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("BindService_","onCreate")
        object :Thread(){
            override fun run() {
              while (!quit){
                  sleep(1000)
                  this@BindService.count++
              }
            }
        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BindService_","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("BindService_","onUnbind")

        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BindService_","onDestroy")
        quit = true
    }

}