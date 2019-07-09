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
    var contentResover: ContentResolver

    init {
        contentResover = context.contentResolver
    }

    fun getAllPhoto(itemCallBack: ((MutableList<FileItem>) -> Unit)) {
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

    fun getAllMusic(itemCallBack: ((MutableList<FileItem>) -> Unit)) {
        val musics = ArrayList<FileItem>()

        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME
        )


        runBlocking {
            val job = GlobalScope.launch {
                var cursor = contentResover.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Audio.AudioColumns.DATE_MODIFIED + " desc"
                )

                var fileId: String

                var fileName: String

                var filePath: String

                while (cursor!!.moveToNext()) {

                    fileId = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.AudioColumns._ID))

                    fileName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME))

                    filePath = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.AudioColumns.DATA))

                    Log.e("ryze_music", "$fileId -- $fileName -- $filePath")


                    val fileItem = FileItem(fileId, filePath, fileName)

                    musics.add(fileItem)

                }

                cursor!!.close()

                cursor = null
            }
            job.join()
            itemCallBack.invoke(musics)

        }

    }

    fun getAllVideo(itemCallBack: ((MutableList<FileItem>) -> Unit)) {
        val videos = ArrayList<FileItem>()
        val projection = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.DISPLAY_NAME
        )

        runBlocking {
            val job = GlobalScope.launch {
                var cursor = contentResover.query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Video.VideoColumns.DATE_MODIFIED + " desc"
                )


                var fileId: String

                var fileName: String

                var filePath: String

                while (cursor!!.moveToNext()) {

                    fileId = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Video.VideoColumns._ID))

                    fileName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME))

                    filePath = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Video.VideoColumns.DATA))


                    Log.e("ryze_video", "$fileId -- $fileName -- $filePath")

                    val fileItem = FileItem(fileId, filePath, fileName)

                    videos.add(fileItem)

                }


                cursor!!.close()
                cursor = null
            }
            job.join()
            itemCallBack.invoke(videos)
        }


    }

}


data class FileItem(
    var fileId: String? = null,
    var fileName: String? = null,
    var filePath: String? = null
)