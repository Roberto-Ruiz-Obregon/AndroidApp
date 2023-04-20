package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.databinding.FragmentoInscripcionBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);

        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()


        val btnInscribirse = root.findViewById<Button>(R.id.button)

        val token: String = "Bearer " + LoginActivity.token
        fun EnrollUser() {

            //Este es un ejemplo de un curso
            //TODO extraer info de curso en vista de Inscripción
          //  val cursoId: String = "64386615c8ec2f0bc8b9dee3"

            val user = Inscription(
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
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    binding.Titulo.text = curso.courseName
                    binding.textoCurso.text = curso.description
                    binding.nombrePonente.text = curso.teacher

                    Glide.with(requireContext())
                        .load(curso.imageUrl)
                        .into(binding.imagen)

                }
            }
        }
    }
    private fun cursoFromID(cursoID: String?, result: CursosObjeto?): Document? {
        for (curso in result!!.data.documents){
            var cursoid = curso._id
            if(cursoid.toString() == cursoID)
                return curso
        }
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}