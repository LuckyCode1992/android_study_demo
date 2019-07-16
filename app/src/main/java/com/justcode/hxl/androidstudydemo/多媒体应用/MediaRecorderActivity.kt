package com.justcode.hxl.androidstudydemo.多媒体应用

import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.permission.RxPermissions
import com.justcode.hxl.progrect0.core.syntactic_sugar.toast
import com.justcode.hxl.progrect0.core.syntactic_sugar.visible
import kotlinx.android.synthetic.main.activity_media_recorder.*
import java.io.File

class MediaRecorderActivity : AppCompatActivity() {


    var soundFile: File? = null
    var recorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null
    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_recorder)

        RxPermissions(this).request(Manifest.permission.RECORD_AUDIO)
            .subscribe {
                if (it) {
                    btn_record.setOnClickListener {
                        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                            "sd卡不存在".toast()
                        } else {
                            soundFile = File(Environment.getExternalStorageDirectory().toString() + "/sound.amr")
                            recorder = MediaRecorder()
                            //设置录音的来源
                            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                            //设置录音的输出格式（必须在设置声音编码之前设置）
                            recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                            //设置声音编码格式
                            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                            //设置保存录音的文件
                            recorder?.setOutputFile(soundFile!!.absolutePath)
                            recorder?.prepare()
                            recorder?.start()
                        }
                    }
                    btn_stop.setOnClickListener {
                        stopRecord()
                        btn_play.visible()
                    }
                    btn_play.setOnClickListener {

                        if (soundFile != null && soundFile?.exists() == true) {
                            mediaPlayer = MediaPlayer()
                            mediaPlayer?.let { player ->
                                player.setDataSource(soundFile!!.absolutePath)
                                player.prepare()
                                player.start()
                            }
                        }

                    }
                } else {

                }
            }

    }

    private fun stopRecord() {
        if (soundFile != null && soundFile?.exists() == true) {
            //停止录音
            recorder?.stop()
            //释放资源
            recorder?.release()
            recorder = null
        }
    }

    override fun onDestroy() {
        stopRecord()
        mediaPlayer?.stop()
        super.onDestroy()

    }

}
