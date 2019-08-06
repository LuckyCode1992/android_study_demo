package com.justcode.hxl.androidstudydemo.传感器

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.progrect0.core.syntactic_sugar.start
import kotlinx.android.synthetic.main.activity_demo4.*
import kotlinx.android.synthetic.main.activity_sensor_manger.*
import kotlinx.android.synthetic.main.file_item.view.*

class SensorMangerActivity : AppCompatActivity() {
    val list: MutableList<SensorData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_manger)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val adapter = MyAdapter(list, this)
        recycle.layoutManager = layoutManager
        recycle.adapter = adapter
        adapter.itemClick = {

        }
        //获取一个传感器管理器
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in sensorList) {
            val sensorType = sensor.type
            var type: String = ""
            var discription: String = ""
            when (sensorType) {
                Sensor.TYPE_ACCELEROMETER -> {
                    type = "TYPE_ACCELEROMETER"
                    discription = "加速度传感器"
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    type = "TYPE_AMBIENT_TEMPERATURE"
                    discription = "外界温度传感器"
                }
                Sensor.TYPE_GAME_ROTATION_VECTOR -> {
                    type = "TYPE_GAME_ROTATION_VECTOR"
                    discription = "转动向量传感器"
                }
                Sensor.TYPE_GRAVITY -> {
                    type = "TYPE_GRAVITY"
                    discription = "重力传感器"
                }
                Sensor.TYPE_GYROSCOPE -> {
                    type = "TYPE_GYROSCOPE"
                    discription = "陀螺仪"
                }
                Sensor.TYPE_LIGHT -> {
                    type = "TYPE_LIGHT"
                    discription = "光照传感器"
                }
                Sensor.TYPE_LINEAR_ACCELERATION -> {
                    type = "TYPE_LINEAR_ACCELERATION"
                    discription = "线性加速度传感器"
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    type = "TYPE_MAGNETIC_FIELD"
                    discription = "磁场传感器"
                }
                Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> {
                    type = "TYPE_MAGNETIC_FIELD_UNCALIBRATED"
                    discription = "未校准磁场传感器"
                }
                Sensor.TYPE_PROXIMITY -> {
                    type = "TYPE_PROXIMITY"
                    discription = "近距离传感器"
                }
                Sensor.TYPE_ROTATION_VECTOR -> {
                    type = "TYPE_ROTATION_VECTOR"
                    discription = "旋转向量传感器"
                }
                Sensor.TYPE_SIGNIFICANT_MOTION -> {
                    type = "TYPE_SIGNIFICANT_MOTION"
                    discription = "有力动作感应器"
                }
                Sensor.TYPE_TEMPERATURE -> {
                    type = "TYPE_TEMPERATURE"
                    discription = "cpu温度感应器"
                }
                Sensor.TYPE_PRESSURE -> {
                    type = "TYPE_PRESSURE"
                    discription = "压力感应器"
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    type = "TYPE_STEP_COUNTER"
                    discription = "计步器"
                }
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    type = "TYPE_RELATIVE_HUMIDITY"
                    discription = "相对湿度感应器"
                }
                Sensor.TYPE_ORIENTATION -> {
                    type = "TYPE_ORIENTATION"
                    discription = "方向感应器"
                }

                else -> {
                    discription = "未知"

                }
            }

            val sensorData = SensorData(sensorName = sensor.name, disp = discription, type = type)

            list.add(sensorData)
        }


        adapter.updale(list)

        btn_data.setOnClickListener {
            start<SensorDataActivity>()
        }

    }
}

data class SensorData(
    var sensorName: String? = null,
    var disp: String? = null,
    var type: String? = null
)

class MyAdapter(var list: MutableList<SensorData>, var context: Context) : RecyclerView.Adapter<MyHolder>() {

    var itemClick: (SensorData) -> Unit = {}
    override fun onCreateViewHolder(parant: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parant.context).inflate(R.layout.file_item, parant, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        with(holder.itemView) {
            tv_id.text = list[position].sensorName
            tv_name.text = list[position].disp
            tv_path.text = list[position].type

            setOnClickListener {
                itemClick.invoke(list[position])
            }
        }

    }

    fun updale(list0: MutableList<SensorData>) {
        list = list0
        notifyDataSetChanged()
    }
}

class MyHolder(view: View) : RecyclerView.ViewHolder(view)
