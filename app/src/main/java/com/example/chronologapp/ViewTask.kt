package com.example.chronologapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ViewTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val startDateEditText = findViewById<EditText>(R.id.startDateEditText)
        val endDateEditText = findViewById<EditText>(R.id.endDateEditText)
        val timesheetListView = findViewById<ListView>(R.id.lstTimesheets)

        startDateEditText.setOnClickListener {
            showDatePickerDialog(startDateEditText)
        }

        endDateEditText.setOnClickListener {
            showDatePickerDialog(endDateEditText)
        }

        val filterButton = findViewById<Button>(R.id.btnFilter)
        filterButton.setOnClickListener {
            val startDate = startDateEditText.text.toString().toInt()
            val endDate = endDateEditText.text.toString().toInt()
            val filteredTimesheets = filterTimesheetsByDate(startDate, endDate)
            displayTimesheets(filteredTimesheets)
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val selectedDate = "$selectedDayOfMonth-${selectedMonth + 1}-$selectedYear"
            editText.setText(selectedDate)
        }, year, month, day).show()
    }

    private fun filterTimesheetsByDate(startDate: Int, endDate: Int): List<timeSheet> {
        return AppData.timesheets.filter { timesheet ->
            val timesheetDate = timesheet.date
            timesheetDate >= startDate && timesheetDate <= endDate
        }
    }

    private fun displayTimesheets(timesheets: List<timeSheet>) {
        val timesheetListView = findViewById<ListView>(R.id.lstTimesheets)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, timesheets.map { it.description })
        timesheetListView.adapter = adapter
    }
}