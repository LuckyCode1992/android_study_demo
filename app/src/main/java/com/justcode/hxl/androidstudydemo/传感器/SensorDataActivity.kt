package com.justcode.hxl.androidstudydemo.传感器

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_sensor_data.*

class SensorDataActivity : AppCompatActivity(), SensorEventListener {

    //实现传感器接口
    //传感器繁盛改变时回掉
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {

        val valurs = event.values
        //获取出发event的传感器类型
        val sensorType = event.sensor.type
        val sb: StringBuilder?
        when (sensorType) {
            //方向传感器
            Sensor.TYPE_ORIENTATION -> {
                tv_fangxiang_z.text = valurs[0].toString()
                tv_fangxiang_x.text = valurs[1].toString()
                tv_fangxiang_y.text = valurs[2].toString()

            }
            //陀螺仪传
            Sensor.TYPE_GYROSCOPE -> {
                tv_tuoluoyi_x.text = valurs[0].toString()
                tv_tuoluoyi_y.text = valurs[1].toString()
                tv_tuoluoyi_z.text = valurs[2].toString()

            }
            //磁场传
            Sensor.TYPE_MAGNETIC_FIELD -> {
                tv_cichang_x.text = valurs[0].toString()
                tv_cichang_y.text = valurs[1].toString()
                tv_cichang_z.text = valurs[2].toString()

            }
            //重力传感器
            Sensor.TYPE_GRAVITY -> {
                tv_zhongli_x.text = valurs[0].toString()
                tv_zhongli_y.text = valurs[1].toString()
                tv_zhongli_z.text = valurs[2].toString()

            }
            //线性加速度传感器
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                tv_jiasu_x.text = valurs[0].toString()
                tv_jiasu_y.text = valurs[1].toString()
                tv_jiasu_z.text = valurs[2].toString()
            }
            //温度传感器
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                tv_wendu.text = valurs[0].toString()
            }
            //湿度传感器
            Sensor.TYPE_RELATIVE_HUMIDITY -> {
                tv_shidu.text = valurs[0].toString()
            }
            //光感传感器
            Sensor.TYPE_LIGHT -> {
                tv_guang.text = valurs[0].toString()

            }
            //压力传感器
            Sensor.TYPE_PRESSURE -> {
                tv_yali.text = valurs[0].toString()
            }
        }
    }

    lateinit var sensorManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_data)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()

        //方向传感器注册监听
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
        //陀螺仪传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            SensorManager.SENSOR_DELAY_GAME
        )
        //磁场传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_GAME
        )
        //重力传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_GAME
        )
        //线性加速度传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
            SensorManager.SENSOR_DELAY_GAME
        )
        //温度传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
            SensorManager.SENSOR_DELAY_GAME
        )
        //湿度传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
            SensorManager.SENSOR_DELAY_GAME
        )
        //光感传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
            SensorManager.SENSOR_DELAY_GAME
        )
        //压力传感器
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
