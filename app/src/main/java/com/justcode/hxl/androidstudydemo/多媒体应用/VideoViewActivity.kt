package com.justcode.hxl.androidstudydemo.多媒体应用

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.MediaController
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_video_view.*

class VideoViewActivity : AppCompatActivity() {
    lateinit var mediaController: MediaController
    lateinit var videoPath: String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        mediaController = MediaController(this)
        videoPath = Uri.parse("android.resource://" + packageName + "/" + R.raw.texiao).toString()
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0x222)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x222 && grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            video.setVideoPath(videoPath)
            //videoview和controller进行关联
            video.setMediaController(mediaController)
            mediaController.setMediaPlayer(video)
            //video获取焦点
            video.requestFocus()

        }
    }
}
