package com.example.chronologapp

import ImageAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AddTask : AppCompatActivity(), View.OnClickListener {
    private val arrTimeSheet = ArrayList<timeSheet>()

    private lateinit var btnDatePicker: Button
    private lateinit var btnStartTimePicker: Button
    private lateinit var btnEndTimePicker: Button
    private lateinit var txtStartTime: EditText
    private lateinit var txtEndTime: EditText
    private lateinit var txtDate: EditText
    private lateinit var txtDescription : EditText
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_timesheet)

        txtDescription = findViewById(R.id.txtDescription)
        btnDatePicker = findViewById(R.id.btn_date)
        btnStartTimePicker = findViewById(R.id.btn_start_time)
        btnEndTimePicker = findViewById(R.id.btn_end_time)
        txtDate = findViewById(R.id.in_date)
        txtStartTime = findViewById(R.id.in_start_time)
        txtEndTime = findViewById(R.id.in_end_time)





        btnDatePicker.setOnClickListener(this)
        btnStartTimePicker.setOnClickListener(this)
        btnEndTimePicker.setOnClickListener(this)

        val categorySpinner = findViewById<Spinner>(R.id.spnCategory)
        val categories = AppData.categories.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categorySpinner.adapter = adapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val btnAddSheet: Button = findViewById(R.id.btnAddSheet)


        btnAddSheet.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            arrTimeSheet.add(timeSheet(txtDate.text.toString().toInt(), txtStartTime.text.toString().toInt(),
                txtEndTime.text.toString().toInt(), txtDescription.text.toString(), selectedCategory, R.id.selectedImage ))

            Toast.makeText(this, "Timesheet Added", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnBackTimesheet: Button = findViewById(R.id.btnBackTimesheet)


        btnBackTimesheet.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }

        val btnSelectImage: Button = findViewById(R.id.btnImageSelection)
        btnSelectImage.setOnClickListener {
            showImageSelectionDialog()
        }


    }

    override fun onClick(v: View) {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)


        when (v.id) {
            R.id.btn_date -> {
                DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                    txtDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                }, mYear, mMonth, mDay).show()

            }
            R.id.btn_start_time -> {
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    txtStartTime.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false).show()
            }
            R.id.btn_end_time -> {
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    if (isValidEndTime(hourOfDay, minute)) {
                        txtEndTime.setText("$hourOfDay:$minute")

                    } else {
                        // Show a message to the user that the end time must be later than the start time
                        Toast.makeText(this, "End time must be later than start time", Toast.LENGTH_SHORT).show()
                    }
                }, mHour, mMinute, false).show()

            }


        }

        }

    private fun isValidEndTime(hourOfDay: Int, minute: Int): Boolean {
        val startTime = txtStartTime.text.toString().split(":")
        val endTime = "$hourOfDay:$minute"
        return endTime > startTime.joinToString(":")
    }

    private fun showImageSelectionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.image_selection, null)
        val gridViewImages = dialogView.findViewById<GridView>(R.id.gridViewImages)
        val imageIds = arrayOf(R.drawable.checked, R.drawable.close, R.drawable.fork,
            R.drawable.internet)

        val adapter = ImageAdapter(this, imageIds)

        gridViewImages.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Select an Image")
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()


        gridViewImages.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            val selectedImageID = imageIds[position]

            val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
            selectedImageView.setImageResource(selectedImageID)

            dialog.dismiss()
        }


    }

}
