package com.justcode.hxl.androidstudydemo.tcp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.tcp.tcp_core.SocketListener
import com.justcode.hxl.androidstudydemo.tcp.tcp_core.SocketUtil
import com.justcode.hxl.progrect0.core.syntactic_sugar.toast
import kotlinx.android.synthetic.main.activity_tcpframe.*
import java.nio.charset.Charset

class TCPFrameActivity : AppCompatActivity() {

    lateinit var handler: Handler
    val socketUtil = SocketUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcpframe)
        handler = Handler()
        socketUtil.isOpenHeart(true)
            .addHeartInterval(1000)
            .addIpPort("192.168.134.1", 6000)
            .addMAXoutTime(5000)
            .addListener(object : SocketListener {
                override fun onConnected() {
                    handler.post {
                        tv_status.text = "状态：连接成功"
                    }
                }

                override fun onDisconnected() {
                    handler.post {
                        tv_status.text = "状态：断开连接"
                    }
                }

                override fun onReconnected() {
                    handler.post {
                        tv_status.text = "状态：重新连接中"
                    }
                }

                override fun onSend(msg: ByteArray) {

                }

                override fun onReceived(msg: String) {
                    handler.post {
                        tv_message.text = msg
                    }
                }

                override fun onError(msg: String) {
                    Looper.prepare()
                    msg.toast()
                    Looper.loop()
                }

                override fun onSendHeart() {

                }

            })
        btn_connect.setOnClickListener {
            socketUtil.connect()
        }
        btn_disconnect.setOnClickListener {
            socketUtil.disConnect()
        }
        btn_send.setOnClickListener {
            val input = et_input.text.toString()
            socketUtil.sendMsg(input.toByteArray(Charset.forName("GBK")))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketUtil.disConnect()
    }
}
