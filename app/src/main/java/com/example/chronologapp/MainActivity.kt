package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chronologapp.AppData.Companion.arrTimeSheet
import com.example.chronologapp.AppData.Companion.categories

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
// User can clicks button if they want to add task
        val btnAddTask: Button = findViewById(R.id.btnAddTask)

        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTask ::class.java)
            startActivity(intent)
        }
// User clicks button when they want to add category
        val btnAddCat: Button = findViewById(R.id.btnAddCat)


        btnAddCat.setOnClickListener {
            val intent = Intent(this, CategoryPage ::class.java)
            startActivity(intent)
        }
// If user wants to View Task
        val btnViewTask: Button = findViewById(R.id.btnViewTasks)

        btnViewTask.setOnClickListener {



            if(arrTimeSheet.isEmpty()){

                Toast.makeText(this, "Please add at least one task to view them ", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, ViewTask ::class.java)
                startActivity(intent)
            }

        }

        val btnCatHours: Button = findViewById(R.id.btnCatHours)


        btnCatHours.setOnClickListener {

            if(categories.isEmpty()){

                Toast.makeText(this, "Please add a category before before calculating ", Toast.LENGTH_SHORT).show()

            }else{
                val intent = Intent(this, TotalHours ::class.java)
                startActivity(intent)


            }
        }


        val btnSetGoal:Button = findViewById(R.id.btnSetGoalMain)
        btnSetGoal.setOnClickListener {

            val intent = Intent(this, SetGoal ::class.java)
            startActivity(intent)



        }





    }
}