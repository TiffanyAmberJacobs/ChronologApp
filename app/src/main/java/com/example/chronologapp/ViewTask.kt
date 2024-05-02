package com.example.chronologapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import com.example.chronologapp.AppData.Companion.arrTimeSheet

class ViewTask : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    private var startDay = 0
    private var startMonth = 0
    private var startYear = 0
    private var endDay = 0
    private var endMonth = 0
    private var endYear = 0

    private var savedStartDay = 0
    private var savedStartMonth = 0
    private var savedStartYear = 0

    private var savedEndYear = 0
    private var savedEndDay = 0
    private var savedEndMonth = 0



    private lateinit var txtStartdate: EditText
    private lateinit var txtEnddate: EditText
    var isMinTimePicker = true
    lateinit var converDate:LocalDate

    private lateinit var startDatePicker: DatePicker
    private lateinit var endDatePicker: DatePicker
    private lateinit var filterButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }




    }


    private fun getTimeCalendar() {

        val c = java.util.Calendar.getInstance()
        startDay = c.get(java.util.Calendar.DAY_OF_MONTH)
        startMonth = c.get(java.util.Calendar.MONTH)
        startYear = c.get(java.util.Calendar.YEAR)
        endDay = c.get(java.util.Calendar.DAY_OF_MONTH)
        endMonth = c.get(java.util.Calendar.MONTH)
        endYear = c.get(java.util.Calendar.YEAR)


    }

    private fun pickTime() {
        getTimeCalendar()
        txtStartdate.setOnClickListener {
          DatePickerDialog(this,this,startYear,startMonth,startYear)
            isMinTimePicker = true
        }
        txtEnddate.setOnClickListener {
            DatePickerDialog(this, this, endYear, endMonth,endYear).show()
            isMinTimePicker = false
        }
    }

    override fun onDateSet(view: DatePicker?, dayOfMonth: Int, month: Int,year:Int) {
        if (isMinTimePicker) {
            savedStartDay = dayOfMonth
            savedStartMonth = month
            savedStartYear = year
            txtStartdate.setText("$savedStartDay:$savedStartMonth:$savedStartYear")
        } else {
            savedEndDay = dayOfMonth
            savedEndMonth = month
            savedEndYear = year
            txtEnddate.setText("$savedEndDay:$savedEndMonth:$savedEndYear")
        }
        isMinTimePicker =!isMinTimePicker
    }

    fun filterTimesheets(start: LocalDate, end: LocalDate): List<timeSheet> {
        return arrTimeSheet.filter { it.date >= start && it.date <= end }
    }





}