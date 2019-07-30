package com.justcode.hxl.androidstudydemo.tcp.tcp_core

interface SocketListener {
    fun onConnected()
    fun onDisconnected()
    fun onReconnected()
    fun onSend(msg: ByteArray)
    fun onReceived(msg: String)
    fun onError(msg: String)
    fun onSendHeart()
}