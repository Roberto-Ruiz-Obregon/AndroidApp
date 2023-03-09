package com.example.kotlin.robertoruizapp

import android.app.Activity
import android.os.Bundle
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding

class MainActivity: Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }


}