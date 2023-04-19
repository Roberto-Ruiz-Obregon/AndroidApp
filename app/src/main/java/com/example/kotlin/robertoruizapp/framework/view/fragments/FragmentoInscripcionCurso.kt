package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoInscripcionBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.view.activities.UserInscription
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel
import com.example.kotlin.robertoruizapp.utils.Constants

class FragmentoInscripcionCurso :  Fragment() {
    private var _binding: FragmentoInscripcionBinding? = null
    private val binding get() = _binding!!
    private var cursoID : String? = null
    private lateinit var viewModel: InscriptionViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    //private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        Log.d("check", "Entrando a vista")
        viewModel = ViewModelProvider(this)[InscriptionViewModel::class.java]
        _binding = FragmentoInscripcionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val intent = Intent (requireContext(), InscriptionViewModel::class.java)

        cursoID = intent.getStringExtra(Constants.CURSO_ID_EXTRA)

        val btnInscribirse = root.findViewById<Button>(R.id.button)

        val token: String = LoginActivity.token
        fun EnrollUser() {

            //val cursoID: String = "Taller 1"
            //val userID: String = "Marco"

            val user = Inscription(
                token,
                cursoID
            )
            viewModel.enrollUser(token, user)

        }

        btnInscribirse.setOnClickListener {
            EnrollUser()
            Log.d("inscribirme boton", "me clickeaste")
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}