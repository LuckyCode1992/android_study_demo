package com.justcode.hxl.androidstudydemo.intent的部分功能

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.annotation.RequiresApi
import android.support.v4.content.CursorLoader
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main7.*


class Main7Activity : AppCompatActivity() {
    val PICK_CONTACT = 0
    var phone0 = ""
    var contactId0 = "1"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        btn_getphone.setOnClickListener {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0x133)
        }

        btn_call.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel:${phone0}")
            startActivity(intent)
        }
        btn_edit.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_INSERT_OR_EDIT
            val data = "content://com.android.contacts/contacts/"+contactId0
            intent.data = Uri.parse(data)
            startActivity(intent)
        }

        btn_home.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }
        btn_web.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val data = "https://www.baidu.com"
            val uri  = Uri.parse(data)
            intent.data = uri
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x133) {
            if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

                startActivityForResult(intent, PICK_CONTACT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_CONTACT -> {
                if (resultCode == Activity.RESULT_OK) {
                    //获取返回得数据
                    val contactData = data!!.data
                    val cursorLoader = CursorLoader(this, contactData, null, null, null, null)
                    //查询联系人信息
                    val cursor = cursorLoader.loadInBackground()
                    //查询指定联系人
                    if (cursor?.moveToFirst() == true) {
                        val contactId = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                        contactId0 = contactId
                        //联系人名字
                        val name = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                ContactsContract.Contacts.DISPLAY_NAME
                            )
                        )
                        val phone = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                        phone0 = phone
//                        var phone = "没有电话号码哦"
                        //根据联系人查询联系人得详细信息
//                        val phones = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
//                            null,
//                            null,
//                            null
//                        )
//                        if (phones.moveToFirst()) {
//                            //取出电话
//                            phone = phones.getString(
//                                phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//                            )
//                        }
                        //关闭游标
//                        phones.close()

                        tv_phone.text = "联系人：${name} —— 电话：${phone}"

                    }
                    //关闭游标
                    cursor?.close()
                }
            }
        }
    }
}
