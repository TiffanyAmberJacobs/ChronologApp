package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAddTask: Button = findViewById(R.id.btnAddTask)


        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTask ::class.java)
            startActivity(intent)
        }

        val btnAddCat: Button = findViewById(R.id.btnAddCat)


        btnAddCat.setOnClickListener {
            val intent = Intent(this, CategoryPage ::class.java)
            startActivity(intent)
        }

        val btnViewTask: Button = findViewById(R.id.btnViewTasks)


        btnViewTask.setOnClickListener {
            val intent = Intent(this, ViewTask ::class.java)
            startActivity(intent)
        }






    }
}