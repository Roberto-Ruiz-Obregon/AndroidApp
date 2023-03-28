package com.example.kotlin.robertoruizapp.framework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartRegisterActivity = findViewById<Button>(R.id.btnStartSignUp0Activity)

        btnStartRegisterActivity.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }

}




