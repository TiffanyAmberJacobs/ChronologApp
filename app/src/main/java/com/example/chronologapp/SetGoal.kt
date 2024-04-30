package com.example.chronologapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class SetGoal : AppCompatActivity(),  View.OnClickListener {
    var arrHours = ArrayList<hourGoals>()
    private var mHour: Int = 0
    private var mMinute: Int = 0

    private lateinit var txtMaxHours: EditText
    private lateinit var txtMinHours: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_set_goal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets



        }
        txtMaxHours = findViewById(R.id.max)
        txtMinHours = findViewById(R.id.min)

        // Correctly set the OnClickListener for txtMinHours and txtMaxHours
        txtMinHours.setOnClickListener(this)
        txtMaxHours.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val c = Calendar.getInstance()

        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)

        when (v.id) {
            R.id.min -> {
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    txtMinHours.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false).show()
            }

            R.id.max -> {
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    txtMaxHours.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false).show()
            }
        }
        // This line might cause issues if the EditTexts are empty or not properly formatted
        // Consider adding validation or handling for empty or invalid inputs
        arrHours.add(hourGoals(txtMinHours.text.toString().toInt(), txtMaxHours.text.toString().toInt()))
    }
    }