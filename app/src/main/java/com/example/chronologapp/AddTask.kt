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
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import com.example.chronologapp.AppData.Companion.arrTimeSheet
import java.time.LocalTime
import android.Manifest
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class AddTask : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val CAMERA_REQUEST_CODE = 100
        private const val PERMISSIONS_REQUEST_CODE = 101
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 102
    }


    private lateinit var btnDatePicker: Button
    private lateinit var btnStartTimePicker: Button
    private lateinit var btnEndTimePicker: Button
    private lateinit var txtStartTime: EditText
    private lateinit var txtEndTime: EditText
    private lateinit var txtDate: EditText
    private lateinit var txtDescription: EditText
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    private var selectedImageData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_timesheet)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permissions
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_CODE)
            }
        } else {
            // Permissions have already been granted
        }



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

            // Check if date, start time, and end time fields are not empty
            if (txtDate.text.isEmpty() || txtStartTime.text.isEmpty() || txtEndTime.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val dateFormatter = DateTimeFormatter.ofPattern("d-M-yyyy")
            val parsedDate = LocalDate.parse(txtDate.text.toString(), dateFormatter)

            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val parsedStartTime = LocalTime.parse(txtStartTime.text.toString(), timeFormatter)

            val parsedEndTime = LocalTime.parse(txtEndTime.text.toString(), timeFormatter)

            if (parsedStartTime.isAfter(parsedEndTime)) {
                Toast.makeText(this, "Start time cannot be later than end time", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Check if end time is not earlier than start time
            if (parsedEndTime.isBefore(parsedStartTime)) {
                Toast.makeText(
                    this,
                    "End time cannot be earlier than start time",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            arrTimeSheet.add(
                timeSheet(
                    parsedDate, parsedStartTime,
                    parsedEndTime, txtDescription.text.toString(), selectedCategory, selectedImageData
                )
            )

            Toast.makeText(this, "Timesheet Added", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnBackTimesheet: Button = findViewById(R.id.btnBackTimesheet)


        btnBackTimesheet.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnSelectImage: Button = findViewById(R.id.btnSelectImage)
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
                    txtStartTime.setText(String.format("%02d:%02d", hourOfDay, minute))
                }, mHour, mMinute, true).show()
            }

            R.id.btn_end_time -> {
                TimePickerDialog(this, { _, hourOfDay, minute ->
                    if (isValidEndTime(hourOfDay, minute)) {
                        txtEndTime.setText(String.format("%02d:%02d", hourOfDay, minute))

                    } else {
                        // Show a message to the user that the end time must be later than the start time
                        Toast.makeText(
                            this,
                            "End time must be later than start time",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, mHour, mMinute, true).show()

            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
            selectedImageView.setImageBitmap(imageBitmap)

            savePhotoToExternalStorage(imageBitmap)
        }
    }

    private fun savePhoto(imageBitmap: Bitmap) {
        val fileName = "photo_${System.currentTimeMillis()}.png"
        val file = File(getExternalFilesDir(null), fileName)
        try {
            val outStream = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
            selectedImageData = file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
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
        val imageIds = arrayOf(
            R.drawable.checked, R.drawable.close, R.drawable.fork,
            R.drawable.internet
        )

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

            selectedImageData = imageIds[position].toString()

            val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
            selectedImageView.setImageResource(imageIds[position])
            dialog.dismiss()
        }

        val btnTakePhoto = dialogView.findViewById<Button>(R.id.btnTakePhoto)
        btnTakePhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CODE)
            } else {
                // Permission is already granted, start the camera intent
                startCameraIntent()
            }
        }

    }

    private var isRequestingWriteExternalStoragePermission = false

    private fun savePhotoToExternalStorage(imageBitmap: Bitmap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it

            isRequestingWriteExternalStoragePermission = true
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
        } else {
            // Permission has already been granted, proceed with saving the photo
            savePhoto(imageBitmap)
        }
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted, start the camera intent
                    startCameraIntent()
                } else {
                    // Permission denied, inform the user
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}






