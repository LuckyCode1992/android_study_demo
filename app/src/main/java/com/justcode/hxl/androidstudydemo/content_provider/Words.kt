package com.justcode.hxl.androidstudydemo.content_provider

import android.net.Uri
import android.provider.BaseColumns

object Words {
    //定义contentprovider 的 authorities
    val AUTHORITY = "com.justcode.hxl.content_provider.DictProvider"

    //定义静态内部类，包含数据列名
    class Word : BaseColumns {
        companion object {
            //定义content 允许操作的数据列
            val _ID = "_id"
            val WORD = "word"
            val DETAIL = "detail"
            //定义content 提供服务的两个Uri
            val DECT_CONTENT_URI = Uri.parse("content://$AUTHORITY/words")
            val WORD_CONTENT_URI = Uri.parse("content://$AUTHORITY/word")
        }
    }
}