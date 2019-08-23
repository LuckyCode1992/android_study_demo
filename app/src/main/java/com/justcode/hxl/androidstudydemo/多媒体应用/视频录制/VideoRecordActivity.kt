package com.justcode.hxl.androidstudydemo.多媒体应用.视频录制

import android.Manifest
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.hardware.Camera
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.androidstudydemo.permission.RxPermissions
import com.justcode.hxl.progrect0.core.syntactic_sugar.gone
import com.justcode.hxl.progrect0.core.syntactic_sugar.visible
import kotlinx.android.synthetic.main.activity_video_record.*
import java.io.File
import java.util.*

class VideoRecordActivity : AppCompatActivity() {
    val TAG = "VideoRecordActivity_"

    var mediaPlayer: MediaPlayer? = null
    var surfaceHolder: SurfaceHolder? = null
    var camera: Camera? = null
    var recorder: MediaRecorder? = null
    var cameraReleaseEnable = true//回收摄像头
    var recorderReleaseEnable = false  //回收recorder
    var playerReleaseEnable = false //回收palyer
    var mStartedFlag = false //录像中标志
    var path: String? = null//录制视频的路径
    var mPlayFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_record)
        RxPermissions(this)
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            )
            .subscribe {
                if (it) {
                    init()
                } else {
                    finish()
                }
            }.dispose()
    }

    fun init() {
        mediaPlayer = MediaPlayer()
        val holder = surface.holder
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                surfaceHolder = holder
                camera?.apply {
                    startPreview()
                    cancelAutoFocus()
                    // 关键代码 该操作必须在开启预览之后进行（最后调用），
                    // 否则会黑屏，并提示该操作的下一步出错
                    // 只有执行该步骤后才可以使用MediaRecorder进行录制
                    // 否则会报 MediaRecorder(13280): start failed: -19
                    unlock()
                }
                cameraReleaseEnable = true
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    surfaceHolder = holder
                    //使用前置摄像头
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
                    camera?.apply {
                        setDisplayOrientation(90)//旋转90度
                        setPreviewDisplay(holder)
                        val params = parameters
                        //注意此处需要根据摄像头获取最优像素，//如果不设置会按照系统默认配置最低160x120分辨率
                        val size = getPreviewSize()
                        params.apply {
                            setPictureSize(size.first, size.second)
                            jpegQuality = 100
                            pictureFormat = PixelFormat.JPEG
                            focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE//1连续对焦
                        }
                        parameters = params
                    }

                } catch (e: Exception) {
                    Log.d(TAG, Log.getStackTraceString(e))
                }

            }

        })

        rl_start_nei.setOnClickListener {
            if (mStartedFlag) {
                stop()
                tv_start.text = "Start"
            } else {
                start()
                tv_start.text = "Stop"
            }
        }
        progress.setOnCircleProgressInter { scaleProgress, progress, max ->
            Log.d("progress_", "scaleProgress:${scaleProgress}-progress:${progress}")
            if (scaleProgress >= 100.0) {
                stop()
            }
        }
    }

    fun start() {
        if (!mStartedFlag) {
            mStartedFlag = true
            //开始计时
//            handler.postDelayed(runnable, maxSec * 10L)
            progress.setProgress(100f)
            recorderReleaseEnable = true
            recorder = MediaRecorder().apply {
                reset()
                setCamera(camera)
                // 设置音频源与视频源 这两项需要放在setOutputFormat之前
                setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                setVideoSource(MediaRecorder.VideoSource.CAMERA)
                //设置输出格式
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                //这两项需要放在setOutputFormat之后 IOS必须使用ACC
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)  //音频编码格式
                //使用MPEG_4_SP格式在华为P20 pro上停止录制时会出现
                //MediaRecorder: stop failed: -1007
                //java.lang.RuntimeException: stop failed.
                // at android.media.MediaRecorder.stop(Native Method)
                setVideoEncoder(MediaRecorder.VideoEncoder.H264)  //视频编码格式
                //设置最终出片分辨率
                setVideoSize(1920, 1080)
                setVideoFrameRate(30)
                setVideoEncodingBitRate(3 * 1024 * 1024)
                //播放时候，前置摄像头 270，后置摄像头90 解决翻转问题
                setOrientationHint(270)
                //设置记录会话的最大持续时间（毫秒）
                setMaxDuration(30 * 1000)
            }
            path = Environment.getExternalStorageDirectory().path + File.separator + ""
            if (path != null) {
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                path = dir.absolutePath + "/" + getDate() + ".mp4"
                Log.d(TAG, "文件路径： $path")
                recorder?.apply {
                    setOutputFile(path)
                    prepare()
                    start()
                }
            }
        }
    }

    fun stop() {
        if (mStartedFlag) {
            progress.gone()
            mStartedFlag = false
            try {
                recorder?.apply {
                    stop()
                    reset()
                    release()
                }
                recorderReleaseEnable = false
                camera?.apply {
                    lock()
                    setPreviewCallback(null)
                    stopPreview()
                    release()

                }
                cameraReleaseEnable = false

                Handler(Looper.getMainLooper()).postDelayed({
                    playRecord()
                }, 500)

            } catch (e: Exception) {
                Log.d(TAG, Log.getStackTraceString(e))
            }
        }
    }

    //播放录像
    private fun playRecord() {
        //修复录制时home键切出再次切回时无法播放的问题
        if (cameraReleaseEnable) {
            Log.d(TAG, "回收摄像头资源")
            camera?.apply {
                lock()
                setPreviewCallback(null)
                stopPreview()
                release()

            }
            cameraReleaseEnable = false
        }
        playerReleaseEnable = true
        mPlayFlag = true

        mediaPlayer?.reset()
        var uri = Uri.parse(path)
        mediaPlayer = MediaPlayer.create(VideoRecordActivity@ this, uri)
        mediaPlayer?.apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDisplay(surfaceHolder)
            setOnCompletionListener {
                //播放解释后再次显示播放按钮

            }
        }
        try {
            mediaPlayer?.prepare()
        } catch (e: Exception) {
            Log.d(TAG, Log.getStackTraceString(e))
        }
        mediaPlayer?.start()

        rl_record.gone()
        btn_next.visible()
    }

    //停止播放录像
    fun stopPlay() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
    }

    //从底层拿camera支持的previewsize，完了和屏幕分辨率做差，diff最小的就是最佳预览分辨率
    private fun getPreviewSize(): Pair<Int, Int> {
        if (camera == null) {
            return Pair(0, 0)
        }
        var bestPreviewWidth: Int = 1920
        var bestPreviewHeight: Int = 1080
        var mCameraPreviewWidth: Int
        var mCameraPreviewHeight: Int
        var diffs = Integer.MAX_VALUE
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val screenResolution = Point(display.width, display.height)
        val availablePreviewSizes = camera!!.parameters.supportedPreviewSizes
        Log.e(TAG, "屏幕宽度 ${screenResolution.x}  屏幕高度${screenResolution.y}")
        for (previewSize in availablePreviewSizes) {
            Log.v(TAG, " PreviewSizes = $previewSize")
            mCameraPreviewWidth = previewSize.width
            mCameraPreviewHeight = previewSize.height
            val newDiffs =
                Math.abs(mCameraPreviewWidth - screenResolution.y) + Math.abs(mCameraPreviewHeight - screenResolution.x)
            Log.v(TAG, "newDiffs = $newDiffs")
            if (newDiffs == 0) {
                bestPreviewWidth = mCameraPreviewWidth
                bestPreviewHeight = mCameraPreviewHeight
                break
            }
            if (diffs > newDiffs) {
                bestPreviewWidth = mCameraPreviewWidth
                bestPreviewHeight = mCameraPreviewHeight
                diffs = newDiffs
            }
            Log.e(TAG, "${previewSize.width} ${previewSize.height}  宽度 $bestPreviewWidth 高度 $bestPreviewHeight")
        }
        Log.e(TAG, "最佳宽度 $bestPreviewWidth 最佳高度 $bestPreviewHeight")
        return Pair(bestPreviewWidth, bestPreviewHeight)
    }

    /**
     * 获取系统时间
     * @return
     */
    fun getDate(): String {
        var ca = Calendar.getInstance()
        var year = ca.get(Calendar.YEAR)           // 获取年份
        var month = ca.get(Calendar.MONTH)         // 获取月份
        var day = ca.get(Calendar.DATE)            // 获取日
        var minute = ca.get(Calendar.MINUTE)       // 分
        var hour = ca.get(Calendar.HOUR)           // 小时
        var second = ca.get(Calendar.SECOND)       // 秒
        return "" + year + (month + 1) + day + hour + minute + second
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlay()
        if (recorderReleaseEnable) recorder?.release()
        if (cameraReleaseEnable) {
            camera?.setPreviewCallback(null)
            camera?.stopPreview()
            camera?.release()

        }
        if (playerReleaseEnable) {
            mediaPlayer?.release()
        }
    }
}
