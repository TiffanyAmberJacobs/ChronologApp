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
        val btnBackReg: Button = findViewById(R.id.btnBackReg)
// Adding User Information to array called arrUser
        arrUser.add(User("user", "user"))
        // to check if reg info is correct
        var foundReg = false


        // adding User Information to Array if password and Confirm password are the same and if not show error message
        btnReg.setOnClickListener {

            // if password and confirm password is the same and all information is the same then it will take user to landing page
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
