package com.example.chronologapp

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class TimesheetAdapter(private val timesheets: List<timeSheet>) : RecyclerView.Adapter<TimesheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesheetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timesheet, parent, false)
        return TimesheetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimesheetViewHolder, position: Int) {
        val timesheet = timesheets[position]
        holder.txtDate.text = timesheet.date.toString()
        holder.txtStartTime.text = timesheet.startTime.toString()                     // Attributed from : https://www.youtube.com/watch?v=UbP8E6I91NA
                                                                                        // Author : Foxandroid
        holder.txtEndTime.text = timesheet.endTime.toString()
        holder.txtDescription.text = timesheet.description
        holder.txtCategory.text = timesheet.category

        val imageFile = timesheet.imageURL?.let { File(it) }
        if (imageFile != null) {
            if (imageFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                holder.imgTimesheet.setImageBitmap(bitmap)
            } else {
                // Handle the case where the image file does not exist
                holder.imgTimesheet.setImageResource(R.drawable.checked)
            }
        }
    }
    override fun getItemCount(): Int {
        return timesheets.size
    }





}