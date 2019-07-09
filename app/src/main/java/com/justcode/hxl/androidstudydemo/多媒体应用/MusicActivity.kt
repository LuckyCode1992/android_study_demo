package com.justcode.hxl.androidstudydemo.多媒体应用

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_music.*
import android.content.Intent.CATEGORY_OPENABLE
import android.content.Intent.ACTION_GET_CONTENT
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.widget.Toast



class MusicActivity : AppCompatActivity() {
    private val FILE_SELECT_CODE = 155
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        btn_chose.setOnClickListener {
            val intent = Intent(ACTION_GET_CONTENT)
            intent.setType("audio/*")
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE)
            } catch (ex: android.content.ActivityNotFoundException) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private val TAG = "ChooseFile"
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> if (resultCode == Activity.RESULT_OK) {
                // Get the Uri of the selected file
                val uri = data!!.data
                Log.d(TAG, "File Uri: " + uri!!.toString())
                // Get the path
                val path = getPath(this, uri)
                Log.d(TAG, "File Path: $path")
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getPath(context: Context, uri: Uri): String? {
        if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            val projection = arrayOf("_data")
            var cursor: Cursor? = null
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow("_data")
                if (cursor!!.moveToFirst()) {
                    return cursor!!.getString(column_index)
                }
            } catch (e: Exception) {
                // Eat it  Or Log it.
            }

        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }
        return null
    }
}
