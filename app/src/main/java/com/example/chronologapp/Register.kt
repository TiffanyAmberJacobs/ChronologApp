package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators.and
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
var arrUser=ArrayList<User>()

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var username: EditText = findViewById(R.id.txtUsername)
        var password: EditText = findViewById(R.id.txtPassword)
        var conPassword: EditText = findViewById(R.id.txtConPass)
        val btnReg: Button = findViewById(R.id.btnRegister)

        arrUser.add(User("user","user"))








        btnReg.setOnClickListener {
            arrUser.add(User(username.text.toString(), password.text.toString()))
            if (password.text.toString() == conPassword.text.toString() && username.text.toString()
                    .isNotEmpty()
            ) {
                Toast.makeText(this, "Registered!!", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
                finish()

            }


        }


    }
}
