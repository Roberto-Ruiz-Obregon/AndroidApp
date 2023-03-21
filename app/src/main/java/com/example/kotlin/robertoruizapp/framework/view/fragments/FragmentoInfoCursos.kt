package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.model.CURSO_ID_EXTRA
import com.example.kotlin.robertoruizapp.model.Document
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter

class FragmentoInfoCursos : AppCompatActivity() {
    private lateinit var binding: FragmentoInfoCursosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentoInfoCursosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cursoID = intent.getIntExtra(CURSO_ID_EXTRA, -1)
        val curso = cursoFromID(cursoID)
        if (curso != null)
        {
            binding.nombreCursoInfo.text = curso.courseName
            binding.descripcionCurso.text = curso.description
            binding.fechaCurso.text = curso.startDate
            binding.tipoModalidad.text = curso.modality
           // binding.nombrePonente.text = curso.teachers



        }
    }

    private fun cursoFromID(cursoID: Int): Document?
    {
        lateinit var data: List<Document>

        for (curso in data){
            var cursoid = curso._id
            if(cursoid.toInt() == cursoID)
                return curso
        }
        return null
    }
}