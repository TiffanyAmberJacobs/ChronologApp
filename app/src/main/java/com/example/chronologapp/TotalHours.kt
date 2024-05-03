package com.example.chronologapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chronologapp.AppData.Companion.arrTimeSheet
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration

class TotalHours : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtStartDate: EditText
    private lateinit var txtEndDate: EditText
    private lateinit var spnCategory: Spinner
    private lateinit var btnApplyFilter: Button
    private lateinit var lblCalcTotalHours : TextView
    private lateinit var lblHoursNumber : TextView

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_total_hours)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtStartDate = findViewById(R.id.txtStartDateCat)
        txtEndDate = findViewById(R.id.txtEndDateCat)
        btnApplyFilter = findViewById(R.id.btnTotalHours)
        lblCalcTotalHours = findViewById(R.id.lblCalcHours)
        lblHoursNumber = findViewById(R.id.lblHoursNumber)

        txtStartDate.setOnClickListener(this)
        txtEndDate.setOnClickListener(this)
        btnApplyFilter.setOnClickListener(this)

        spnCategory = findViewById(R.id.spnCatHours)
        val categories = AppData.categories.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spnCategory.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtStartDateCat, R.id.txtEndDateCat -> {
                showDatePickerDialog(v as EditText)
            }
            R.id.btnTotalHours -> {
                applyFilter()
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val selectedDate = LocalDate.now()

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val newSelectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            if (editText.id == R.id.txtStartDateCat) {
                startDate = newSelectedDate
            } else {
                endDate = newSelectedDate
            }
            editText.setText(newSelectedDate.format(DateTimeFormatter.ofPattern("d-M-yyyy")))
        }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth).show()
    }

    private fun applyFilter() {
        val selectedCategory = spnCategory.selectedItem.toString()
        if (startDate == null || endDate == null) {
            Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show()
            return
        }

        val filteredTimesheets = arrTimeSheet.filter { timesheet ->
            timesheet.date.isAfter(startDate!!.minusDays(1)) && timesheet.date.isBefore(endDate!!.plusDays(1)) && timesheet.category == selectedCategory
        }

        val totalHours = filteredTimesheets.sumOf { timesheet ->
            val startTime = LocalTime.parse(timesheet.startTime.toString(), DateTimeFormatter.ofPattern("HH:mm"))
            val endTime = LocalTime.parse(timesheet.endTime.toString(), DateTimeFormatter.ofPattern("HH:mm"))
            val duration = Duration.between(startTime, endTime)
            duration.toHours()
        }

        lblCalcTotalHours.text = "Total hours spent on $selectedCategory between $startDate and $endDate:"
        lblHoursNumber.text = "$totalHours hours"
    }
}
