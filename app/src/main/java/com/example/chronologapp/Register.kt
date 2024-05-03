package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators.and
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.MimeTypeFilter.matches
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
var arrUser=ArrayList<User>()
var foundReg = false
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
        var conPassword: EditText = findViewById(R.id.txtConPass)   // assigning text fields and buttons to variables
        val btnReg: Button = findViewById(R.id.btnRegister)
        val btnBackReg: Button = findViewById(R.id.btnBackReg)










        btnReg.setOnClickListener {



            arrUser.add(User(username.text.toString(), password.text.toString()))
            if (password.text.toString() == conPassword.text.toString() && username.text.toString()
                    .isNotEmpty()
            ) {
                foundReg = true
                Toast.makeText(this, "Successfully Registered!!", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
                finish()
            // if password is not the same as confirm password and info is not all entered, an error message will show up
            };if (foundReg == false) {
            Toast.makeText(
                this,
                "Password is not the same as confirm password or enter all Information needed!!",
                Toast.LENGTH_SHORT
            ).show()


        }


        }
        btnBackReg.setOnClickListener {
            // Navigate to back to the landing page
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}
