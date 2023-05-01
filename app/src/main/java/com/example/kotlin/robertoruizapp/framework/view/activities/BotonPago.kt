package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoPagoCurso

/**
 * BotonPago class that manages the activity actions
 */
class BotonPago : AppCompatActivity() {
    private lateinit var botonIrAVista2: Button

    /**
     * When the activity is created sets up binding and viewmodel
     * alsi initializes the manageIntent, Binding and Listener methods
     *
     * @param savedInstanceState the state of the activity / fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmento_info_cursos)

        botonIrAVista2 = findViewById(R.id.button_inscribirme)
        botonIrAVista2.setOnClickListener {
            val intent = Intent(this, FragmentoPagoCurso::class.java)
            startActivity(intent)
        }
    }
}