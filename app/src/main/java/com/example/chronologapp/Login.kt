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
        val btnBackLogin : Button = findViewById(R.id.btnBackLogin)
        var found = false
        //checking if username and password is in the arrUser
        btnLogin.setOnClickListener {


            for (i in arrUser.indices) {

                if(username.text.toString()==(arrUser[i].userName) && password.text.toString()==(arrUser[i].password)) {

                    //if username and password is in array, user is signed in and takes them to main page
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    found = true
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    break
                }
                //if not information not found in arruser, show error message
            };if(found == false){

            Toast.makeText(this, "cannot find user", Toast.LENGTH_SHORT).show()

        }
            btnBackLogin.setOnClickListener {
                // Navigate to back to the landing page
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }


        }


    }
}