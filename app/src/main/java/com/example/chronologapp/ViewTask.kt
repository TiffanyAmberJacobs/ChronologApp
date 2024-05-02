package com.example.chronologapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import com.example.chronologapp.AppData.Companion.arrTimeSheet
import java.time.format.DateTimeFormatter

class ViewTask : AppCompatActivity() {


    var startDate: EditText = findViewById(R.id.txtStartDate)
    var endDate: EditText = findViewById(R.id.txtEndDate)

    var btnFilter: Button = findViewById(R.id.btnFilter)
    var list: ListView = findViewById(R.id.timelist)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ArrayAdapter for ListView
        var adapter = ArrayAdapter<timeSheet>(this, android.R.layout.activity_list_item)
        list.adapter = adapter
        adapter = TimeSheetAdapter(this, arrTimeSheet)

        // Set up DatePickerDialog for start date
        startDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, { _, day, month, year ->
                val selectedDate = LocalDate.of(day, month - 1, year)
                startDate.setText(selectedDate.toString())
            }, day, month, year).show()
        }

        // Set up DatePickerDialog for end date
        endDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, { _, day, month, year  ->
                val selectedDate = LocalDate.of(year, month - 1, day)
                endDate.setText(selectedDate.toString())
            }, day, month, year).show()
        }

        // Set up filter button
        btnFilter.setOnClickListener {
            val startDateStr = startDate.text.toString()
            val endDateStr = endDate.text.toString()
            if (startDateStr.isNotEmpty() && endDateStr.isNotEmpty()) {
                val startDate =
                    LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("d-M-yyyy"))
                val endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("d-M-yyyy"))
                val filteredTimesheets =
                    arrTimeSheet.filter { it.date.isAfter(startDate) && it.date.isBefore(endDate) }
                adapter.clear()
                adapter.addAll(filteredTimesheets)
                adapter.notifyDataSetChanged()
            }
        }
    }


    class TimeSheetAdapter(context: Context, private val timesheets: List<timeSheet>) : ArrayAdapter<timeSheet>(context, R.layout.activity_view_task) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView?: LayoutInflater.from(context).inflate(R.layout.activity_view_task, parent, false)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val textViewDescription = view.findViewById<TextView>(R.id.txtDescription)
            val textViewDate = view.findViewById<TextView>(R.id.in_date)
            val textStartTime = view.findViewById<TextView>(R.id.in_start_time)
            val textEndTIme= view.findViewById<TextView>(R.id.in_end_time)
            val timeSheet = timesheets[position]
            imageView.setImageResource(timeSheet.imageURL) // Assuming imageURL is an integer resource ID
            textViewDescription.text = timeSheet.description
            textViewDate.text = timeSheet.date.toString()
            textStartTime.text=timeSheet.startTime.toString()
            textStartTime.text=timeSheet.endTime.toString()
            return view
        }
    }
}


