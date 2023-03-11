package com.example.kotlin.robertoruizapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity: Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()
        getCourseList()
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result:CursosObjeto? = repository.getCursos()
            Log.d("Salida", result?.data?.documents!![0].courseName )
        }
    }

}