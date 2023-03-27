package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.model.Document
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.model.CursosObjeto
import com.example.kotlin.robertoruizapp.model.Data
import com.example.kotlin.robertoruizapp.model.Repository
import com.example.kotlin.robertoruizapp.model.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentoInfoCursos : AppCompatActivity() {
    private lateinit var binding: FragmentoInfoCursosBinding
    private var cursoID : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentoInfoCursosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursoID = intent.getStringExtra(CURSO_ID_EXTRA)

        // Carga los datos
        lateinit var data: List<Document>
        getCourseList()

    }


    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    binding.nombreCursoInfo.text = curso.courseName
                    binding.descripcionCurso.text = curso.description
                    binding.fechaCurso.text = curso.startDate
                    binding.tipoModalidad.text = curso.modality
                    binding.nombrePonente.text = curso.teacher

                }
            }
        }
    }

    private fun cursoFromID(cursoID: String?, result:CursosObjeto?): Document? {
        for (curso in result!!.data.documents){
            var cursoid = curso._id
            if(cursoid.toString() == cursoID)
                return curso
        }
        return null
    }
}

