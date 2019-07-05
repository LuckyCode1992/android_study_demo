package com.justcode.hxl.androidstudydemo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",
            Toast.LENGTH_SHORT
        ).show()
    }

}

class MyReceiver2 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",
            Toast.LENGTH_SHORT
        ).show()
    }

}

class MyReceiver10 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "10,接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

class MyReceiver20 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = getResultExtras(true)
        val message = bundle.getString("message")
        Toast.makeText(
            context,
            "20,接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}-新消息：${message}",
            Toast.LENGTH_SHORT
        ).show()
        abortBroadcast()
    }
}

class MyReceiver30 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "30,接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",
            Toast.LENGTH_SHORT
        ).show()
        val bundle = Bundle()
        bundle.putString("message", "30传来消息")
        setResultExtras(bundle)
    }
}