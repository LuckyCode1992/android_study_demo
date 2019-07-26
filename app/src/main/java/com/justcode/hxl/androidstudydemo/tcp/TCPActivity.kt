package com.justcode.hxl.androidstudydemo.tcp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_tcp.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket
import android.system.Os.socket
import java.io.DataInputStream
import java.nio.charset.Charset


class TCPActivity : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var thread: Thread
    lateinit var threadSend: Thread
    lateinit var threadReceive: Thread
    var s: Socket? = null
    //处理socket的输入流
    var os: OutputStream? = null

    var bufferedReader: BufferedReader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcp)
        Log.d("socket_", "hello")
        handler = Handler()

        btn_connect.setOnClickListener {
            thread = Thread(Runnable {
                connect()
            }
            )
            thread.start()
        }
        btn_disconnect.setOnClickListener {

        }
        btn_send.setOnClickListener {
            val message = et_input.text.toString()
            threadSend = Thread(Runnable {
                os?.write(message.toByteArray(charset("utf-8")))
            })
            threadSend.start()

        }

        btn_register.setOnClickListener {

            threadReceive = Thread(Runnable {
                if (s != null && s?.isConnected == true && s?.isClosed == false) {
                        val inputStream = s?.getInputStream()
                    val input = DataInputStream(inputStream)
                    val b = ByteArray(1024)
                    var len = 0
                    var reponse = ""
                    while (true){
                        len = input.read(b)
                        reponse = String(b, 0, len, Charset.forName("GBK"))

                        Log.d("socket_",reponse)
                        handler.post {
                            tv_message.text = reponse
                        }
                    }

                }
            })
            threadReceive.start()

        }
    }

    fun connect() {
        try {
            s = Socket("192.168.134.1", 6000)
            os = s?.outputStream
        } catch (e: Exception) {
            Log.d("socket_", Log.getStackTraceString(e))
        }
    }
}



