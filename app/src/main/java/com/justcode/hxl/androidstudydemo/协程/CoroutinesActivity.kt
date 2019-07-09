package com.justcode.hxl.androidstudydemo.协程

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.多媒体应用.ContentUtil
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import java.lang.Exception

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        btn_1.setOnClickListener {
            val job = GlobalScope.launch {
                delay(1000L)
                Log.d("GlobalScope_", "World")
                Log.d("GlobalScope_", Thread.currentThread().name)
            }
            Log.d("GlobalScope_", "Hello")
            runBlocking {
                Log.d("GlobalScope_", Thread.currentThread().name)
            }
        }
        btn_2.setOnClickListener {
            runBlocking {
                val job = GlobalScope.launch {
                    delay(1000L)
                    Log.d("GlobalScope_", "World")
                    Log.d("GlobalScope_", Thread.currentThread().name)
                    try {
                        tv_text.setText("测试一下")
                    } catch (e: Exception) {
                        Log.d("GlobalScope_", Log.getStackTraceString(e))
                    }


                }
                Log.d("GlobalScope_", "Hello")
                job.join()
                Log.d("GlobalScope_", Thread.currentThread().name)

            }
        }
        /**
         * 。runBlocking 与 coroutineScope 的主要区别在于后者在等待所有子协程执行完毕时不会阻塞当前线程。
         */
        btn_3.setOnClickListener {
            runBlocking {
                GlobalScope.launch {
                    delay(3000L)
                    Log.d("GlobalScope_", "GlobalScope_launch")
                    Log.d("GlobalScope_", Thread.currentThread().name)
                }
                coroutineScope {
                    launch {
                        delay(2000L)
                        Log.d("GlobalScope_", "coroutineScope_launch")
                        Log.d("GlobalScope_", Thread.currentThread().name)
                    }
                    delay(1000L)
                    Log.d("GlobalScope_", "coroutineScope")
                    Log.d("GlobalScope_", Thread.currentThread().name)
                }
            }
        }
        btn_4.setOnClickListener {
            runBlocking {
                launch {
                    doWork()
                    Log.d("GlobalScope_", "World")
                    Log.d("GlobalScope_", Thread.currentThread().name)
                }
            }
        }
        val contentUtil = ContentUtil(this)
        btn_5.setOnClickListener {
            contentUtil.getAllPhoto {
                try {
                    tv_text.setText("测试一下")
                } catch (e: Exception) {
                    Log.d("GlobalScope_", Log.getStackTraceString(e))
                }
            }
        }
    }

    suspend fun doWork() {
        delay(1000L)
        Log.d("GlobalScope_", "World")
        Log.d("GlobalScope_", Thread.currentThread().name)
    }
}
