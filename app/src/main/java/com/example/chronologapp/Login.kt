package com.example.chronologapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var username: EditText = findViewById(R.id.txtLoginUsername)
        var password: EditText = findViewById(R.id.txtloginPassword)
        val btnLogin: Button = findViewById(R.id.BtnSignin)


        btnLogin.setOnClickListener {
            var found = false

            for (i in arrUser.indices) {

                if(username.text.toString()==(arrUser[i].userName) && password.text.toString()==(arrUser[i].password)){


                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    found = true
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                break
                }

            };if(found == false){

            Toast.makeText(this, "cannot find user", Toast.LENGTH_SHORT).show()

        }


        }


    }
}