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
import androidx.fragment.app.FragmentActivity
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
import com.example.kotlin.robertoruizapp.framework.view.activities.MainActivity
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
    val token: String = "Bearer " + LoginActivity.token
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

        //val token: String = "Bearer " + LoginActivity.token

        val btnRegreso = root.findViewById<Button>(R.id.buttonRegreso)

        btnRegreso.setOnClickListener {
            goHome()
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()

            val result: CursosObjeto? = repository.getCursosNoFilter(token)

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    _binding.textView27.text = curso.courseName
                    _binding.Modalidad.text = curso.modality
                    _binding.Horario.text = curso.schedule
                    if (curso.modality == "Remoto"){
                        _binding.textView34.text = curso.accessLink
                        _binding.AvisoZoom.text = "Ingresa a la sesión de zoom con tu nombre para que se te permita el acceso"
                    }
                    else{
                        _binding.textView34.text = curso.address
                        _binding.AvisoZoom.text = ""
                    }

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(curso.startDate)
                    val enddate = inputFormat.parse(curso.endDate)
                    val formattedDate = outputFormat.format(date)
                    val endformattedDate = outputFormat.format(enddate)

                    _binding.Fecha.text = formattedDate
                    _binding.fechaFin.text = endformattedDate

                    Glide.with(requireContext())
                        .load(curso.imageUrl)
                        .into(_binding.ivDetailProgramImagen)

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

    private fun goHome() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }




}