package com.justcode.hxl.androidstudydemo.多媒体应用

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Visualizer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutCompat
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TabHost
import android.widget.TextView
import com.justcode.hxl.androidstudydemo.R
import kotlin.math.min

class MusicPlayerActivity : AppCompatActivity() {

    val fileItem by lazy {
        intent.getParcelableExtra<FileItem>("fileItem")
    }

    //定义播放声音的MediaPlayer
    lateinit var mediaPlayer: MediaPlayer
    //定义系统的示波器
    lateinit var visualizer: Visualizer
    //定义系统的均衡器
    lateinit var equalizer: Equalizer
    //定义系统的重低音控制器
    lateinit var bassBoost: BassBoost
    //定义系统的预设控制器
    lateinit var presetReverb: PresetReverb
    lateinit var layout: LinearLayout
    val reverbNames = ArrayList<Short>()
    val reverbVals = ArrayList<String>()

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //设置控制音乐声音
        volumeControlStream = AudioManager.STREAM_MUSIC
        layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        setContentView(layout)
        requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0X123)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x123 && grantResults != null) {
            Log.d("music_", fileItem.toString())
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(fileItem.filePath)
            mediaPlayer.prepare()
            //初始化示波器
            setupVisualizer()
            //初始化均衡器
            setupEqualizer()
            //初始化重低音控制器
            setuoBassboost()
            //初始化预设控制器
            setPresetReverb()
            //播放音乐
            mediaPlayer.start()
        }
    }

    private fun setupVisualizer() {

        //创建Visualizerview组件，用于显示示波器图像
        val visualizerView = MyVisualizerView(this)
        visualizerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (120f * resources.displayMetrics.density).toInt()
        )
        //将view组件添加到layout容器
        layout.addView(visualizerView)
        //以mediaplayer的audiosessionid创建visulazer
        //相当于设置visualizer负责显示该mediaplayer的音频数据
        visualizer = Visualizer(mediaPlayer.audioSessionId)
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        //为visulizer 设置监听器
        visualizer.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
            override fun onFftDataCapture(visualizer: Visualizer, fft: ByteArray, samplingRate: Int) {

            }

            override fun onWaveFormDataCapture(visualizer: Visualizer, waveform: ByteArray, samplingRate: Int) {
                //用waveform波形数据更新visualzerview
                visualizerView.updateVisualizer(waveform)
            }

        }, Visualizer.getMaxCaptureRate() / 2, true, false)
        visualizer.enabled = true
    }

    private fun setupEqualizer() {
        equalizer = Equalizer(0, mediaPlayer.audioSessionId)
        equalizer.enabled = true
        val eqTititle = TextView(this)
        eqTititle.text = "均衡器"
        layout.addView(eqTititle)
        //获取均衡器支持的最大值，最小值
        val minEQLevel = equalizer.bandLevelRange[0]
        val maxEQLevel = equalizer.bandLevelRange[1]
        //获取均衡器控制支持的所有频率
        val bands = equalizer.numberOfBands
        for (i in 0 until bands) {
            val eqTextView = TextView(this)
            //创建一个textview显示频率
            eqTextView.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            eqTextView.gravity = Gravity.CENTER_HORIZONTAL
            //设置均衡器控制的频率
            eqTextView.text = (equalizer.getCenterFreq(i.toShort()) / 1000).toString() + "Hz"
            layout.addView(eqTextView)
            //创建一个水平排列的layout
            val temLayout = LinearLayout(this)
            temLayout.orientation = LinearLayout.HORIZONTAL
            //创建显示均衡器的最小值
            val minDBTextView = TextView(this)
            minDBTextView.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            minDBTextView.text = (minEQLevel / 100).toString() + "dB"

            val maxDBTextView = TextView(this)
            maxDBTextView.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            maxDBTextView.text = (maxEQLevel / 100).toString() + "dB"

            val layoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.weight = 1f

            //定义seekbar作为调整工具
            val bar = SeekBar(this)
            bar.layoutParams = layoutParams
            bar.max = maxEQLevel - minEQLevel
            bar.progress = equalizer.getBandLevel(i.toShort()).toInt()
            bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
                    //设置该频率的均衡值
                    equalizer.setBandLevel(i.toShort(), (progress + minEQLevel).toShort())
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
            temLayout.addView(minDBTextView)
            temLayout.addView(bar)
            temLayout.addView(maxDBTextView)
            layout.addView(temLayout)


        }
    }

    private fun setuoBassboost() {

    }

    private fun setPresetReverb() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

}


class MyVisualizerView(context: Context) : View(context) {
    //byte数组保存波形抽样点的值
    var bytes: ByteArray? = null
    var points: FloatArray? = null
    val paint = Paint()
    val rect = Rect()
    var type: Byte = 0

    init {
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
    }

    fun updateVisualizer(ftt: ByteArray) {
        bytes = ftt
        //通知组件重绘
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //当用户触碰该组件，切换波形类型
        if (event.action != MotionEvent.ACTION_DOWN) {
            return false
        }
        type++
        if (type >= 3) {
            type = 0
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (bytes == null) {
            return
        }
        //绘制白色背景
        canvas.drawColor(Color.WHITE)
        //使用rect对象记录组件的宽高
        rect.set(0, 0, width, height)
        when (type) {
            //绘制块状的波形图
            0.toByte() -> for (i in 0 until bytes!!.size - 1) {

                val left = (width * i / (bytes!!.size - 1)).toFloat()
                //根据波形值计算该矩形的高宽
                val top = rect.height() - (bytes!![i + 1] + 128).toByte() * rect.height() / 128
                val right = left + 6
                val bottom = rect.height()
                canvas.drawRect(left, top.toFloat(), right, bottom.toFloat(), paint)
            }
            //绘制柱状波形图
            1.toByte() -> kotlin.run {
                var i = 0
                while (i < bytes!!.size - 1) {
                    val left = rect.width() * i / (bytes!!.size - 1)
                    val top = rect.height() - (bytes!![i + 1] + 128).toByte() * rect.height() / 128
                    val right = left + 6
                    val bottom = rect.height()
                    canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
                    i += 18

                }
            }
            //绘制曲线波形图
            2.toByte() -> {
                if (points == null || points!!.size < bytes!!.size * 4) {
                    points = FloatArray(bytes!!.size * 4)
                }
                for (i in 0 until bytes!!.size - 1) {
                    //计算i 的点的x坐标
                    points!!.set(i * 4, rect.width() * i / (bytes!!.size - 1).toFloat())
                    //根据bytes【i】的值，计算i的点的y坐标
                    points!!.set(
                        i * 4 + 1,
                        rect.height() / 2 + (bytes!![i] + 128).toByte() * 128 / (rect.height() / 2).toFloat()
                    )
                    //计算i+1得点的x坐标
                    points!!.set(i * 4 + 2, rect.width() * (i + 1) / (bytes!!.size - 1).toFloat())
                    //计算y的坐标
                    points!!.set(
                        i * 4 + 3,
                        rect.height() / 2 + (bytes!![i + 1] + 128).toByte() * 128 / (rect.height() / 2).toFloat()
                    )
                }
                canvas.drawLines(points, paint)
            }

        }

    }
}
