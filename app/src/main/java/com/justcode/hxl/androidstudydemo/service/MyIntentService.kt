package com.justcode.hxl.androidstudydemo.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

/**
 * intentService 是在子线程执行
 */
class MyIntentService :IntentService("MyIntentService"){
    override fun onHandleIntent(intent: Intent?) {
        val endTime = System.currentTimeMillis() + 20 * 1000
        Log.d("Service_", "onHandleIntent")
        while (System.currentTimeMillis() < endTime) {
            synchronized(this) {
                (this as Object).wait(endTime - System.currentTimeMillis())
            }
        }
        Log.d("Service_", "耗时任务完成")
        Log.d("Service_", "MyIntentService"+Thread.currentThread().toString())
    }

}