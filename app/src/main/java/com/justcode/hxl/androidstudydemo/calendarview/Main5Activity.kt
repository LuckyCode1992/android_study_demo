package com.justcode.hxl.androidstudydemo.calendarview

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import com.justcode.hxl.androidstudydemo.R
import kotlinx.android.synthetic.main.activity_main5.*
import java.util.*

class Main5Activity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        calendar.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(calendarView: CalendarView, year: Int, month: Int, dayofMonth: Int) {
                tv_date.text = "${year}年${month}月${dayofMonth}日"
            }

        })
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        var hour = c.get(Calendar.HOUR)
        var min = c.get(Calendar.MINUTE)
        datepicker.init(year, month, day) { view, year0, month0, day0 ->
            year = year0
            month = month0
            day = day0
        }
        timepicker.setOnTimeChangedListener(object :TimePicker.OnTimeChangedListener{
            override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
                tv_timepicker.text = "${p1}-${p2}"
            }

        })


        datepicker.setOnDateChangedListener(object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                tv_datepicker.text = "${p1}-${p2}-${p3}"
            }
        })
    }
}
