package com.justcode.hxl.androidstudydemo.content_provider

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_content_provider_words.*

class ContentProvider_wordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_words)
        btn_insert.setOnClickListener {
            val word = et_input.text.toString()
            val detail = et_input2.text.toString()
            val values = ContentValues()
            values.put(Words.Word.WORD, word)
            values.put(Words.Word.DETAIL, detail)
            contentResolver.insert(Words.Word.DECT_CONTENT_URI, values)
        }
        btn_search.setOnClickListener {
            val key = et_key.text.toString()
            val cursor = contentResolver.query(
                Words.Word.DECT_CONTENT_URI,
                null,
                "word like ? or detail like ?",
                arrayOf("$key", "$key"),
                null
            )

            tv_show.text = converCursorToList(cursor).toString()
        }
    }

    fun converCursorToList(cursor: Cursor): ArrayList<Map<String, String>> {
        val result = ArrayList<Map<String, String>>()
        while (cursor.moveToNext()) {
            val map = HashMap<String, String>().apply {
                put("word", cursor.getString(1))
                put("detail", cursor.getString(2))
            }
            result.add(map)
        }
        return result
    }
}
