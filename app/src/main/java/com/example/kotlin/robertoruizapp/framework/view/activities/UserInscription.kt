package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.get


class UserInscription  : AppCompatActivity() {
/*

    private lateinit var viewModel: InscriptionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmento_inscripcion)

        Log.d("check", "Entrando a vista")
        val btnInscribirse = findViewById<Button>(R.id.button)

        val token: String = LoginActivity.token

        fun EnrollUser() {

            val cursoID: String = "Taller 1"
            //val userID: String = "Marco"

            val user = Inscription(
                cursoID,
                token
            )
            viewModel.enrollUser(token, user)

        }

        btnInscribirse.setOnClickListener {
            val intent = Intent(this, UserInscription::class.java)
            EnrollUser()
            Log.d("inscribirme boton", "me clickeaste")
            startActivity(intent)
        }
    }
 */
}