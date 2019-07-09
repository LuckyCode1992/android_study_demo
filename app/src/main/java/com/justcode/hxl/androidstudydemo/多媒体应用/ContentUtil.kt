package com.justcode.hxl.androidstudydemo.多媒体应用

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.ArrayList


class ContentUtil(var context: Context) {

    lateinit var contentResover: ContentResolver
    fun getAllPhoto(itemCallBack: ((MutableList<FileItem>) -> Unit)) {
        contentResover = context.contentResolver
        val photos = ArrayList<FileItem>()

        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DISPLAY_NAME
        )
        runBlocking {
            val job = GlobalScope.launch {


                //    desc 按降序排列
                //projection 是定义返回的数据，selection 通常的sql 语句，例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
                var cursor = contentResover.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc"
                )


                var imageId: String? = null

                var fileName: String

                var filePath: String

                while (cursor!!.moveToNext()) {

                    imageId = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.ImageColumns._ID))

                    fileName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME))

                    filePath = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA))

                    val fileItem = FileItem(imageId, filePath, fileName)

                    Log.e("ryze_photo", "$imageId -- $fileName -- $filePath")


                    photos.add(fileItem)


                }
                cursor!!.close()

                cursor = null

            }
            job.join()
            itemCallBack.invoke(photos)
        }
    }
}


data class FileItem(
    var fileId: String? = null,
    var fileName: String? = null,
    var filePath: String? = null
)