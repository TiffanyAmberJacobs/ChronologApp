package com.example.chronologapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimesheetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtDate: TextView = itemView.findViewById(R.id.txtDate)
    val txtStartTime: TextView = itemView.findViewById(R.id.txtStartTime)          // Attributed from : https://www.youtube.com/watch?v=UbP8E6I91NA
                                                                                    // Author : https://www.youtube.com/watch?v=UbP8Ea6I91NA
    val txtEndTime: TextView = itemView.findViewById(R.id.txtEndTime)
    val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
    val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
    val imgTimesheet: ImageView = itemView.findViewById(R.id.imgTimesheet)
}