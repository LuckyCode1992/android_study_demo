package com.justcode.hxl.androidstudydemo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",Toast.LENGTH_SHORT).show()
    }

}
class MyReceiver2:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"接收到intent的action：${intent?.action}\n消息内容：${intent?.getStringExtra("msg")}",Toast.LENGTH_SHORT).show()
    }

}