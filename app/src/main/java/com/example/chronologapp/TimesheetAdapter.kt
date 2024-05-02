package com.example.chronologapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chronologapp.AppData.Companion.arrTimeSheet

class TimesheetAdapter(private val timesheets: List<timeSheet>) : RecyclerView.Adapter<TimesheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesheetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timesheet, parent, false)
        return TimesheetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimesheetViewHolder, position: Int) {
        val timesheet = timesheets[position]
        holder.txtDate.text = timesheet.date.toString()
        holder.txtStartTime.text = timesheet.startTime.toString()
        holder.txtEndTime.text = timesheet.endTime.toString()
        holder.txtDescription.text = timesheet.description
        holder.txtCategory.text = timesheet.category

        timesheet.imageURL?.let { loadImage(holder.imgTimesheet, it) }
    }

    override fun getItemCount(): Int {
        return timesheets.size
    }

    private fun loadImage(imageView: ImageView, imageResourceId: Int) =
        imageView.setImageResource(imageResourceId)
}
