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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.databinding.FragmentoInscripcionBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoInscritoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FragmentoInscripcionExitosa : Fragment(){
    private lateinit var _binding: FragmentoInscritoBinding
    private var cursoID : String? = null
    private lateinit var viewModel: InscriptionViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    //private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        viewModel = ViewModelProvider(this)[InscriptionViewModel::class.java]
        _binding = FragmentoInscritoBinding.inflate(inflater, container, false)
        val root: View = _binding.root
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);

        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()

        val token: String = "Bearer " + LoginActivity.token


        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosNoFilter()

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {


                    Glide.with(requireContext())
                        //.load(curso.imageUrl)
                        //.into(_binding.ivDetailProgramImagen)

                }
            }
        }
    }
    private fun cursoFromID(cursoID: String?, result: CursosObjeto?): Document? {
        for (curso in result?.data?.documents!!){
            var cursoid = curso._id
            if(cursoid == cursoID)
                return curso
        }
        return null
    }



    /*
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        resultID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA)

        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()

    }

    private fun initializeBinding() {
        binding = FragmentoInscritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MENSAJEEE", "ME VENGOOOOOOO")
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosNoFilter()

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(resultID,result)
                if (curso != null)
                {
                    binding.textView27.text = curso.courseName
                    binding.textView32.text = curso.modality
                    binding.textView34.text = curso.accessLink

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(curso.startDate)
                    val formattedDate = outputFormat.format(date)

                    binding.textView31.text = formattedDate



                    }

                    val imageView = findViewById<ImageView>(R.id.ivDetailProgramImagen)

                    Glide.with(this@FragmentoInscripcionExitosa)
                        .load(curso)
                        .into(imageView)

                }
            }
        }

    private fun cursoFromID(resultID: String?, result: CursosObjeto?): Document? {
        for (curso in result!!.data.documents) {
            var cursoid = curso._id
            if(cursoid == resultID){
                Log.d("CursoFROM_ID", cursoid + "and" + resultID)
                return curso
            }
        }
        return null
    }

     */
}