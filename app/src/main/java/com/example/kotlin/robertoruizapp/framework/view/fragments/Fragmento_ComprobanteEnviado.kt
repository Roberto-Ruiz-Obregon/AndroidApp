package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentoComprobanteenviadoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.MainActivity
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class Fragmento_ComprobanteEnviado: Fragment()  {

    private lateinit var binding: FragmentoComprobanteenviadoBinding
    private var cursoID: String? = null
    private var topics: MutableList<String> = mutableListOf<String>("")
    private var status: Array<String?> = arrayOf<String?>("", "Gratuito", "Pagado")
    private var modality: Array<String?> = arrayOf<String?>("", "Remoto", "Presencial")
    private var topicsObject: List<com.example.kotlin.robertoruizapp.data.network.model.Topic.Document>? = arrayListOf()
    private var topicSelected: String? = null
    private var statusSelected = ""
    private var modalitySelected = ""
    private var courseName = ""
    private var postalCode = ""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentoComprobanteenviadoBinding.inflate(inflater, container, false)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA)
        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()

        val root = binding.root
        val btnRegreso : Button = root.findViewById(R.id.boton_regreso)
        btnRegreso.setOnClickListener {
            goHome()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos(
                courseName,
                postalCode,
                modalitySelected,
                statusSelected,
                topicSelected
            )

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    binding.NombreCurso.text = curso.courseName

                    Glide.with(this@Fragmento_ComprobanteEnviado)
                        .load(curso.imageUrl)
                        .into(binding.imagenCurso)

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

    private fun goHome() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }

}