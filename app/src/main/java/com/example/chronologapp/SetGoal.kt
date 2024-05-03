package com.example.chronologapp

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chronologapp.AppData.Companion.dailyGoal

import java.util.Calendar

class SetGoal : AppCompatActivity(),TimePickerDialog.OnTimeSetListener {

      private var minHour = 0
      private var minMinute = 0
    private var maxHour = 0
    private var maxMinute = 0
    private var SavedMinHour = 0    //assigning variables to GUI and placeholder variables to save the minutes
    private var SavedMinMinutes = 0
    private var SavedMaxHour = 0
    private var SavedMaxMinutes = 0
    private lateinit var txtMaxHours: EditText
    private lateinit var txtMinHours: EditText
    var isMinTimePicker = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_set_goal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
         txtMinHours = findViewById(R.id.min)
        txtMaxHours= findViewById(R.id.max)
         val btnSetGoal:Button = findViewById(R.id.btnSetGoal)
        pickTime()

btnSetGoal.setOnClickListener {
    val minHourStr = txtMinHours.text.toString().trim()
    val maxHourStr = txtMaxHours.text.toString().trim()

    if (minHourStr.isNotEmpty() && maxHourStr.isNotEmpty()) {
        val minHour = minHourStr.split(":")[0].toIntOrNull()
        val minMinute = minHourStr.split(":")[1].toIntOrNull()   // Splits the time correctly to let it save into the variables  and make it parsable
        val maxHour = maxHourStr.split(":")[0].toIntOrNull()
        val maxMinute = maxHourStr.split(":")[1].toIntOrNull()

        if (minHour!= null && minMinute!= null && maxHour!= null && maxMinute!= null) {
            dailyGoal.add(hourGoals(minHour, minMinute, maxHour, maxMinute))
        }
    } else {
        Toast.makeText(this, "Both fields are required.", Toast.LENGTH_SHORT).show()
    }
    for(i in dailyGoal.indices){
        val goal = dailyGoal[i]
        Toast.makeText(this, "Start Time: ${goal.maxHours} ${goal.maxMinutes}, End Time: ${goal.minHours}${goal.minMinutes}", Toast.LENGTH_SHORT).show()
    }



}

        val btnBack:Button = findViewById(R.id.btnBackGoalTime)
        btnBack.setOnClickListener {

            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)


        }




            }

  private fun getTimeCalendar() {

         val c = Calendar.getInstance()
         minHour = c.get(Calendar.HOUR_OF_DAY)
         minMinute = c.get(Calendar.MINUTE)  // gets specific time or date
      maxHour = c.get(Calendar.HOUR_OF_DAY)
      maxMinute = c.get(Calendar.MINUTE)
     }

    private fun pickTime() {
        getTimeCalendar()
        txtMinHours.setOnClickListener {
            TimePickerDialog(this, this, minHour, minMinute, true).show()  // gets the calender and assign it correctly  while displaying it to the user
            isMinTimePicker = true
        }
        txtMaxHours.setOnClickListener {
            TimePickerDialog(this, this, maxHour, maxMinute, true).show()
            isMinTimePicker = false
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (isMinTimePicker) {
            SavedMinHour = hourOfDay
            SavedMinMinutes = minute
            txtMinHours.setText("$SavedMinHour:$SavedMinMinutes")  //sets the text to the saved Hours and minutes
        } else {
            SavedMaxHour = hourOfDay
            SavedMaxMinutes = minute
            txtMaxHours.setText("$SavedMaxHour:$SavedMaxMinutes")
        }
        isMinTimePicker =!isMinTimePicker
    }



}












