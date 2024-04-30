package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chronologapp.databinding.ActivityLoginBinding
import com.example.chronologapp.databinding.ActivityRegisterBinding

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegister:Button = findViewById(R.id.btnRegisterHomepg)
        val btnLogin: Button = findViewById(R.id.btnSigninHomepg)



        btnRegister.setOnClickListener {


            val intent = Intent(this,Register::class.java)
        startActivity(intent)

        }

        btnLogin.setOnClickListener {


            val intent = Intent(this,Login::class.java)
            startActivity(intent)

        }


    }
}