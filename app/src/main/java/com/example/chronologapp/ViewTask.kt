package com.example.chronologapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chronologapp.AppData.Companion.arrTimeSheet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ViewTask : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtStartDate: EditText
    private lateinit var txtEndDate: EditText
    private lateinit var btnApplyFilter: Button
    private lateinit var recyclerView: RecyclerView

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        txtStartDate = findViewById(R.id.txtStartdate)
        txtEndDate = findViewById(R.id.txtEndDate)
        btnApplyFilter = findViewById(R.id.btnFilter)
        recyclerView = findViewById(R.id.recTimeList)

        txtStartDate.setOnClickListener(this)
        txtEndDate.setOnClickListener(this)
        btnApplyFilter.setOnClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtStartdate, R.id.txtEndDate -> {
                showDatePickerDialog(v as EditText)
            }
            R.id.btnFilter -> {
                applyFilter()
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val selectedDate = LocalDate.now()

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val newSelectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            if (editText.id == R.id.txtStartdate) {
                startDate = newSelectedDate
            } else {
                endDate = newSelectedDate
            }
            editText.setText(newSelectedDate.format(DateTimeFormatter.ofPattern("d-M-yyyy")))
        }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth).show()
    }

    private fun applyFilter() {
        if (startDate == null || endDate == null) {
            Toast.makeText(this, "Please enter dates", Toast.LENGTH_SHORT).show()
            return
        }

        val filteredTimesheets = arrTimeSheet.filter { timesheet ->
            timesheet.date.isAfter(startDate!!.minusDays(1)) && timesheet.date.isBefore(endDate!!.plusDays(1))
        }

        // Assuming you have a TimesheetAdapter that takes a list of timesheets and displays them in the RecyclerView
        val adapter = TimesheetAdapter(filteredTimesheets)
        recyclerView.adapter = adapter
    }
}
