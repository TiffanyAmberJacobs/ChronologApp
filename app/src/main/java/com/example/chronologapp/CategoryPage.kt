package com.example.chronologapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CategoryPage : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var categoryContainer: LinearLayout
    private lateinit var categoryNameInput: EditText
    private lateinit var addCategoryButton: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_category_page)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            scrollView = findViewById(R.id.scrollView)
            categoryContainer = findViewById(R.id.categoryContainer)
            categoryNameInput = findViewById(R.id.txtCatName)
            addCategoryButton = findViewById(R.id.btnCreatecat)

            addCategoryButton.setOnClickListener {
                val categoryName = categoryNameInput.text.toString()
                if (categoryName.isNotEmpty()) {
                    addCategory(categoryName)
                    categoryNameInput.text.clear() // Clear the input field
                }
            }
        }

        private fun addCategory(name: String) {
            val categoryView = TextView(this).apply {
                text = name
                // Set other properties like textSize, textColor, etc.
            }
            categoryContainer.addView(categoryView)
        }
    }