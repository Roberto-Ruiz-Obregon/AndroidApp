package com.example.kotlin.robertoruizapp

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding

class MainActivity: Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val spinnerSex = findViewById<Spinner>(R.id.spnSex)
        val spinnerOcuppation = findViewById<Spinner>(R.id.spnOcuppation)
        val spinnerEducation = findViewById<Spinner>(R.id.spnEducation)

        val listaSex = listOf("Hombre", "Mujer", "Prefiero no decir")
        val listaEducation = listOf("Ninguno", "Primaria", "Secundaria", "Preparatoria", "Universidad")
        val listaOcuppation = listOf("Estudiante", "Trabajador", "Obrero", "Licuado", "Ingeniero")


        val adaptadorSex = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSex)
        spinnerSex.adapter = adaptadorSex
        val adaptadorOccupation = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaOcuppation)
        spinnerOcuppation.adapter = adaptadorOccupation
        val adaptadorEducation = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaEducation)
        spinnerEducation.adapter = adaptadorEducation
    }

}