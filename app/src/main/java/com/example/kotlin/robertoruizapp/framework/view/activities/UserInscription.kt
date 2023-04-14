package com.example.kotlin.robertoruizapp.framework.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel


class UserInscription  : AppCompatActivity() {
    private lateinit var viewModel: InscriptionViewModel
    private var cursoID: String? = null
    private var userID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmento_inscripcion)


        fun inscription() {


        }
    }
}