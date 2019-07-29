package com.justcode.hxl.androidstudydemo.tcp.tcp_core

import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.util.concurrent.Executors

class SocketUtil {
    val TAG: String = "socket_util"
    var ip: String = ""
    var port: Int = 0
    var listener: SocketListener? = null
    var openHeart: Boolean = false
    var maxOutTime: Long = Long.MAX_VALUE
    var heartInterval: Long = 5 * 1000
    var heartPackage: ByteArray = byteArrayOf("LL".toByte())
    var socket: Socket? = null
    var isConnected: Boolean = false
    var inputStream: InputStream? = null
    var inputStreamReader: InputStreamReader? = null
    var bufferedReader: BufferedReader? = null
    var outputStream: OutputStream? = null
    var buffer = ByteArray(1024)
    val fixedThreadPool = Executors.newFixedThreadPool(5)
    var heartThread: Thread? = null
    var receiverThread: Thread? = null
    //最后的发送时间
    var last_send_time: Long = 0
    //最后的接收时间
    var last_rec_time: Long = 0


    fun addIpPort(ip: String, port: Int): SocketUtil {
        this.ip = ip
        this.port = port
        return this
    }


    fun addListener(listerer: SocketListener): SocketUtil {
        this.listener = listener

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

    fun connect(): SocketUtil {
        //断开原来的socket
        disConnect()
        //建立新的socket
        //判断当前是在什么线程 如果在主线程，则在线程池中开启新线程
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            fixedThreadPool.execute(Runnable {
                connectStart()
            })

        } else {
            connectStart()
        }

        return this
    }

   private fun connectStart() {
        try {
            socket = Socket(ip, port)
            socket?.let { socket ->
                val isConnect = socket.isConnected
                if (isConnect) {
                    inputStream = socket.getInputStream()
                    inputStreamReader = InputStreamReader(inputStream)
                    bufferedReader = BufferedReader(inputStreamReader)

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

            try {
                Thread.sleep(1000)

                /**
                 * 当前时间距离上次发送时间（任意发送）的间隔大于最大心跳间隔时，发送心跳。
                 */
                if (System.currentTimeMillis() - last_send_time >= heartInterval) {
                    sendHeart()
                }
                /**
                 * 如果当前时间 大于 最后一次接收时间+超时时间，则表示，连接超时，socket 断开了
                 */
                if (System.currentTimeMillis() > (last_rec_time + maxOutTime)) {
                    isConnected = false
                }
                //如果断开，就重新连接服务器
                if (!isConnected) {
                    Log.d(TAG, "onReconnect")
                    listener?.onReconnected()
                    disConnect()
                    connectStart()
                }
            } catch (e: Exception) {
                Log.d(TAG, Log.getStackTraceString(e))
            }

        })
        heartThread?.start()
    }


   private fun sendHeart() {
        try {
            outputStream?.write(heartPackage)
            outputStream?.flush()

            Log.d(TAG, "sendHeart")

            if (openHeart) {
                last_send_time = System.currentTimeMillis()
            }

            //判断套接字 读写 部分是否关闭，关闭则表示socket 断开
            if (socket?.isInputShutdown == true || socket?.isOutputShutdown == true) {
                isConnected = false
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
                    var readLen = inputStream?.read(buffer) ?: 0
                    if (readLen > 0) {
                        var data = ByteArray(readLen)
                        System.arraycopy(buffer, 0, data, 0, readLen)
                        listener?.onReceived(data)
                        Log.d(TAG, "onReceived" + ":" + String(data))
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

                        if (bufferedReader != null) {
                            bufferedReader?.close()
                            bufferedReader = null
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader?.close()
                            inputStreamReader = null
                        }
                        if (inputStream != null) {
                            inputStream?.close()
                            inputStream = null
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

