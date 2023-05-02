package com.example.kotlin.robertoruizapp.framework.view.activities

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.ActivityInfoMisCursosBinding

import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*

class InfoMisCursos : AppCompatActivity() {
    private lateinit var binding: ActivityInfoMisCursosBinding
    private var cursoID : String? = null
    private lateinit var currentFragment: Fragment
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        cursoID = intent.getStringExtra(CURSO_ID_EXTRA)

        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()

    }

    private fun initializeBinding() {
        binding = ActivityInfoMisCursosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val token: String = "Bearer " + LoginActivity.token
            val result: CursosObjeto? = repository.getCursos(token,"", "", "", "", null)

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    binding.nombreCursoInfo.text = curso.courseName
                    binding.descripcionCurso.text = curso.description
                    binding.tipoModalidad.text = curso.modality
                    binding.nombrePonente.text = curso.teacher
                    binding.miHorario.text = curso.schedule

                    if (curso.modality == "Remoto"){
                        binding.ubicacionCurso.text = curso.accessLink
                    }
                    else{
                        binding.ubicacionCurso.text = curso.address
                    }
                    //binding.fechaCurso.text = curso.startDate

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(curso.startDate)
                    val enddate = inputFormat.parse(curso.endDate)
                    val formattedDate = outputFormat.format(date)
                    val endformattedDate = outputFormat.format(enddate)

                    binding.fechaCurso.text = formattedDate
                    binding.fechafin.text = endformattedDate


                    val imageView = findViewById<ImageView>(R.id.imageView)

                    Glide.with(this@InfoMisCursos)
                        .load(curso.imageUrl)
                        .into(imageView)

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
}

