package com.justcode.hxl.androidstudydemo.tcp.tcp_core

import android.os.Looper
import android.util.Log
import java.io.*
import java.net.Socket
import java.nio.charset.Charset
import java.util.concurrent.Executors

class SocketUtil {
    val TAG: String = "socket_util"
    var ip: String = ""
    var port: Int = 0
    var listener: SocketListener? = null
    var openHeart: Boolean = false
    var maxOutTime: Long = Long.MAX_VALUE
    var heartInterval: Long = 5 * 1000
    var heartPackage = "心跳 ".toByteArray(Charset.forName("GBK"))
    var socket: Socket? = null
    var isConnected: Boolean = false
    var outputStream: OutputStream? = null
    val fixedThreadPool = Executors.newFixedThreadPool(5)
    var heartThread: Thread? = null
    var receiverThread: Thread? = null
    //最后的发送时间
    var last_send_time: Long = Long.MAX_VALUE
    //最后的接收时间
    var last_rec_time: Long = Long.MAX_VALUE

    var isClose = false


    fun addIpPort(ip: String, port: Int): SocketUtil {
        this.ip = ip
        this.port = port
        return this
    }


    fun addListener(listerer: SocketListener): SocketUtil {
        this.listener = listerer

        return this
    }

    fun isOpenHeart(openHeart: Boolean): SocketUtil {
        this.openHeart = openHeart
        return this
    }

    fun addMAXoutTime(time: Long): SocketUtil {
        this.maxOutTime = time
        return this
    }

    fun addHeartInterval(heartInterval: Long): SocketUtil {
        this.heartInterval = heartInterval
        return this
    }

    fun connect() {
        isClose = false
        //断开原来的socket
        disConnectStart()
        //建立新的socket
        //判断当前是在什么线程 如果在主线程，则在线程池中开启新线程
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            fixedThreadPool.execute(Runnable {
                connectStart()
            })

        } else {
            connectStart()
        }

    }

    private fun connectStart() {
        try {
            socket = Socket(ip, port)
            socket?.let { socket ->
                val isConnect = socket.isConnected
                if (isConnect) {
                    outputStream = socket.getOutputStream()

                    isConnected = true

                    listener?.onConnected()

                    Log.d(TAG, "Connected")

                    //连接成功之后，打开心跳线程
                    openHeartThread()

                    //连接成功之后，打开数据接收线程
                    openReceiveThread()


                }
            }

        } catch (e: Exception) {
            Log.d(TAG, Log.getStackTraceString(e))
        }

    }

    private fun openHeartThread() {
        //打开心跳才继续
        if (!openHeart) {
            return
        }
        //防止重复创建心跳线程
        if (heartThread != null) {
            return
        }
        heartThread = Thread(Runnable {

            //如果连接 则
            while (!isClose) {
                try {

                    Thread.sleep(1000)

                    sendHeart()

                    /**
                     * 如果当前时间 大于 最后一次接收时间+超时时间，则表示，连接超时，socket 断开了
                     *
                     * 目前这里不好模拟 后台发送数据，就是用app端发送数据为准
                     */
                    var isReconnect = false
                    if (System.currentTimeMillis() > (last_send_time + maxOutTime)) {
                        isReconnect = true
                    } else {
                        isReconnect = false
                    }
                    //如果断开，就重新连接服务器
                    if (isReconnect) {
                        Log.d(TAG, "onReconnect")
                        listener?.onReconnected()
                        disConnectStart()
                        connectStart()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, Log.getStackTraceString(e))
                }
            }


        })
        heartThread?.start()
    }


    private fun sendHeart() {
        try {
            outputStream?.write(heartPackage)
            outputStream?.flush()

            Log.d(TAG, "sendHeart")


            //判断套接字 读写 部分是否关闭，关闭则表示socket 断开
            if (socket?.isInputShutdown == true || socket?.isOutputShutdown == true) {
                isConnected = false
            } else {
                isConnected = true
            }
            if (isConnected) {
                last_send_time = System.currentTimeMillis()
            }
            listener?.onSendHeart()
        } catch (e: Exception) {
            Log.d(TAG, Log.getStackTraceString(e))
            isConnected = false
        }
    }

    private fun openReceiveThread() {
        if (receiverThread != null) {
            return
        }
        receiverThread = Thread(Runnable {
            while (isConnected) {
                try {
                    val input = DataInputStream(socket?.inputStream)
                    val b = ByteArray(1024)
                    var len = 0
                    var reponse = ""
                    while (true) {
                        len = input.read(b)
                        reponse = String(b, 0, len, Charset.forName("GBK"))
                        listener?.onReceived(reponse)
                        Log.d(TAG, "onReceived:$reponse")
                        last_rec_time = System.currentTimeMillis()
                    }

                } catch (e: Exception) {
                    Log.d(TAG, Log.getStackTraceString(e))
                    listener?.onError("读取数据出错了")
                }
            }
        })
        receiverThread?.start()
    }

    fun disConnect() {
        isClose = true
        disConnectStart()
    }

    private fun disConnectStart() {
        try {
            closeHeartThread()
            closeReceiverThread()
            socket?.let { mSocket ->
                if (isConnected) {
                    isConnected = false

                    if (!mSocket.isClosed) {
                        if (!mSocket.isInputShutdown) {
                            mSocket.shutdownInput()
                        }
                        if (!mSocket.isOutputShutdown) {
                            mSocket.shutdownOutput()
                        }
                        if (outputStream != null) {
                            outputStream?.close()
                            outputStream = null
                        }

                        mSocket.close()
                    }
                    socket = null

                    listener?.onDisconnected()
                    Log.d(TAG, "onDisconnected")
                }

            }
        } catch (e: Exception) {
            Log.d(TAG, Log.getStackTraceString(e))
            listener?.onDisconnected()
        }
    }

    private fun closeReceiverThread() {
        if (receiverThread != null) {
            receiverThread?.interrupt()
            receiverThread = null
            Log.d(TAG, "关闭 接收数据的线程")
        }
    }

    private fun closeHeartThread() {
        if (heartThread != null) {
            heartThread?.interrupt()
            heartThread = null
            Log.d(TAG, "关闭 心跳线程")
        }
    }

    fun sendMsg(msg: ByteArray) {
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            fixedThreadPool.execute { sendMsgStart(msg) }

        } else {
            sendMsgStart(msg)
        }
    }

    private fun sendMsgStart(msg: ByteArray) {
        try {
            outputStream?.write(msg)
            outputStream?.flush()


            last_send_time = System.currentTimeMillis()


            if (socket?.isInputShutdown == true || socket?.isOutputShutdown == true) {
                isConnected = false
            }

            listener?.onSend(msg)
            Log.e(TAG, "onSend")

        } catch (e: Exception) {
            listener?.onError("发送失败")
            Log.e(TAG, Log.getStackTraceString(e))
        }

    }
}

