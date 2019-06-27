package com.justcode.hxl.androidstudydemo.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    lateinit var binder: BindService.MyBinder
    var catService: IMyAidlInterface? = null

    val coon = object : ServiceConnection {

        //断开连接回调
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("BindService_", "onServiceDisconnected")
        }

        //连接成功回调
        override fun onServiceConnected(componentName: ComponentName, ibinder: IBinder) {
            Log.d("BindService_", "onServiceConnected")
            binder = ibinder as BindService.MyBinder
        }

    }
    val conn_room = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            catService = null

        }

        override fun onServiceConnected(componentName: ComponentName?, ibinder: IBinder?) {
            //获取远程service的onbind方法返回的对象的代理
            catService = IMyAidlInterface.Stub.asInterface(ibinder)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val intent = Intent()
        intent.setClass(this, BindService::class.java)
        btn_bind.setOnClickListener {
            bindService(intent, coon, Service.BIND_AUTO_CREATE)
        }
        btn_unbind.setOnClickListener {
            unbindService(coon)
        }
        btn_get.setOnClickListener {
            Toast.makeText(this, "service的count：${binder.count}", Toast.LENGTH_SHORT).show()
        }

        btn_start_nor.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, NorService::class.java)
            startService(intent)
        }
        btn_start_intentservice.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MyIntentService::class.java)
            startService(intent)
        }
        btn_bind_rooom.setOnClickListener {
            val intent = Intent()
            intent.action = "com.justcode.hxl.androidstudydemo.service.action.AIDL_SERVICE"
            intent.`package` = packageName
            bindService(intent, conn_room, Service.BIND_AUTO_CREATE)
        }
        btn_get_room.setOnClickListener {
            Toast.makeText(this, "name:${catService?.color}-weight:${catService?.wight}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn_room)
    }
}
