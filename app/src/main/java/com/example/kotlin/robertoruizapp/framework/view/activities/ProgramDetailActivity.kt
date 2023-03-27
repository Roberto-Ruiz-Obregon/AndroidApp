package com.example.kotlin.robertoruizapp.framework.view.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.kotlin.robertoruizapp.databinding.ActivityProgramDetailBinding
import com.example.kotlin.robertoruizapp.utils.Constants

class ProgramDetailActivity : Activity() {
    private lateinit var binding: ActivityProgramDetailBinding
    private var programUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        manageIntent()
        initializeBinding()
    }


    private fun initializeBinding(){
        binding = ActivityProgramDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun manageIntent(){
        if(intent != null){
            programUrl = intent.getStringExtra(Constants.URL_PROGRAM)
            Log.d("Salida", programUrl.toString())
        }
    }


}