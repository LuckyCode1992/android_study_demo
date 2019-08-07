package com.justcode.hxl.androidstudydemo.传感器

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_compass.*

class CompassActivity : AppCompatActivity(), SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    var isAcc = false
    var isfie = false
    override fun onSensorChanged(event: SensorEvent) {
        val sensorType = event.sensor.type
        when (sensorType) {

            Sensor.TYPE_ORIENTATION -> {
                val degree = event.values[0]
                Log.d("degree_",degree.toString())
                val ra = RotateAnimation(
                    currentDegree,
                    -degree,
                    Animation.RELATIVE_TO_SELF,
                    0.5F,
                    Animation.RELATIVE_TO_SELF,
                    0.5F
                )
                ra.duration = 200
                iv_compass.startAnimation(ra)
                currentDegree = -degree
            }
            Sensor.TYPE_ACCELEROMETER -> {
                mAccelerometerValues = event.values
                isAcc = true
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                mMagneticValues = event.values
                isfie = true
            }
        }
        if (isAcc && isfie) {
//            calculateOrientation()
            isAcc = false
            isfie = false
        }

    }

    private fun calculateOrientation() {
        val values = FloatArray(3)// 最终结果
        val R = FloatArray(9)// 旋转矩阵
        SensorManager.getRotationMatrix(R, null, mAccelerometerValues, mMagneticValues)// 得到旋转矩阵
        SensorManager.getOrientation(R, values)// 得到最终结果
        var azimuth = Math.toDegrees(values[0].toDouble()).toFloat()// 航向角
//        if (azimuth < 0) {
//            azimuth += 360f
//        }
        //azimuth = azimuth / 5 * 5// 做了一个处理，表示以5°的为幅度
        val pitch = Math.toDegrees(values[1].toDouble()).toFloat()// 俯仰角
        val roll = Math.toDegrees(values[2].toDouble()).toFloat()// 翻滚角

        val cu = Math.abs(currentDegree)
        val de = Math.abs(azimuth)
        val cha = Math.abs(cu - de)

        val ra = RotateAnimation(
            currentDegree,
            -azimuth,
            Animation.RELATIVE_TO_SELF,
            0.5F,
            Animation.RELATIVE_TO_SELF,
            0.5F
        )
        ra.duration = 200
        iv_compass.startAnimation(ra)
        currentDegree = -azimuth
    }


    var currentDegree = 0f
    lateinit var sensorManager: SensorManager
    var mAccelerometerValues = FloatArray(3)// 用于保存加速度值
    var mMagneticValues = FloatArray(3)// 用于保存地磁值
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
        // 初始化加速度传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
        // 初始化地磁场传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
