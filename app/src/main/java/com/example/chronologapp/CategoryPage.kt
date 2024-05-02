package com.example.chronologapp

import android.content.Intent
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
    val arrCategory = ArrayList<Category>()
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
           //when user enters category, and clicks on button, it add category to scroll view and clears the editbox
            addCategoryButton.setOnClickListener {
                val categoryName = categoryNameInput.text.toString()
                if (categoryName.isNotEmpty()) {
                    addCategory(categoryName)
                    categoryNameInput.text.clear()
                    AppData.categories.add(Category(categoryName))
                }

                val btnBack: Button = findViewById(R.id.btnBack)


                btnBack.setOnClickListener {
                    val intent = Intent(this, MainActivity ::class.java)
                    startActivity(intent)
                }
            }
        }

        private fun addCategory(name: String) {
            val categoryView = TextView(this).apply {
                text = name

            }
            categoryContainer.addView(categoryView)
        }
    }
