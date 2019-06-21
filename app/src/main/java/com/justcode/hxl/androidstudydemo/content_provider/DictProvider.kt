package com.justcode.hxl.androidstudydemo.content_provider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import java.lang.IllegalArgumentException

class DictProvider : ContentProvider() {

    lateinit var dpOpenHelper: MyDatabaseHelper

    companion object {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        val WORDS = 1
        val WORD = 2
    }

    init {
        //为mathcer 注册Uri
        matcher.addURI(Words.AUTHORITY, "words", WORDS)
        matcher.addURI(Words.AUTHORITY, "word/#", WORD)
    }


    override fun onCreate(): Boolean {
        dpOpenHelper = MyDatabaseHelper(this.context, "myDict.db3", null, 1)
        return true
    }

    override fun getType(uri: Uri): String? {
        return when (matcher.match(uri)) {
            //如果uri代表操作全部数据
            WORDS ->
                "vnd.android.cursor.dir/com.justcode.hxl.dict"
            WORD -> "vnd.android.cursor.item/com.justcode.hxl.dict"
            else -> throw  IllegalArgumentException("未知Uri:" + uri)

        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        where: String?,
        whereArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        val db = dpOpenHelper.readableDatabase
        return when (matcher.match(uri)) {
            WORDS ->
                db.query("dict", projection, where, whereArgs, null, null, sortOrder)
            WORD -> {
                val id = ContentUris.parseId(uri)
                var whereClause = Words.Word._ID + "=" + id
                if (where != null && "" != where) {
                    whereClause = whereClause + " and " + where
                }
                db.query("dict", projection, whereClause, whereArgs, null, null, sortOrder)
            }
            else -> throw  IllegalArgumentException("未知Uri:$uri")
        }

    }

    override fun insert(uri: Uri, value: ContentValues?): Uri? {
        val db = dpOpenHelper.readableDatabase
        when (matcher.match(uri)) {
            WORDS -> {
                val rowId = db.insert("dict", Words.Word._ID, value)
                if (rowId > 0) {
                    val wordUri = ContentUris.withAppendedId(uri, rowId)
                    context?.contentResolver?.notifyChange(wordUri, null)
                    return wordUri
                }
            }
            else -> throw IllegalArgumentException("未知Uri:$uri")
        }
        return null
    }

    override fun update(uri: Uri, value: ContentValues?, where: String?, whereArgs: Array<String>?): Int {
        val db = dpOpenHelper.writableDatabase

        val num: Int
        when (matcher.match(uri)) {
            WORDS -> {
                num = db.update("dict", value, where, whereArgs)
            }
            WORD -> {
                val id = ContentUris.parseId(uri)
                var whereCause = Words.Word._ID + "=" + id
                if (!TextUtils.isEmpty(whereCause)) {
                    whereCause = whereCause + " and " + where
                }
                num = db.update("dict", value, whereCause, whereArgs)
            }
            else -> throw  IllegalArgumentException("未知Uri:$uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return num
    }

    override fun delete(uri: Uri, where: String?, whereArgs: Array<String>?): Int {
        val db = dpOpenHelper.readableDatabase
        val num: Int
        when (matcher.match(uri)) {
            WORDS -> {
                num = db.delete("dict", where, whereArgs)
            }
            WORD -> {
                val id = ContentUris.parseId(uri)
                var whereClause = Words.Word._ID + "=" + id
                if (!TextUtils.isEmpty(whereClause)) {
                    whereClause = whereClause + " and " + where
                }
                num = db.delete("dict", whereClause, whereArgs)
            }
            else -> throw  IllegalArgumentException("未知Uri:$uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return num
    }


}

class MyDatabaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    private val CREATE_TABLE_SQL = "create table dict(_id integer primary key autoincrement, word , detail)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SQL)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.d("dp_dp_dp", "onUpgrade: oldVersion:${p1}  newVersion:$p2")

    }

}