package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.landing_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegister:Button = findViewById(R.id.btnReg)
        val btnLogin: Button = findViewById(R.id.btnSign)


//User clicks on Register button should take user to register page
        btnRegister.setOnClickListener {


            val intent = Intent(this,Register::class.java)
        startActivity(intent)

        }
// User clicks on Login button it should take user to login page
        btnLogin.setOnClickListener {


            val intent = Intent(this,Login::class.java)
            startActivity(intent)

        }


    }
}