package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*

class FragmentoInfoCursos : AppCompatActivity() {
    private lateinit var binding: FragmentoInfoCursosBinding
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
        binding = FragmentoInfoCursosBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    binding.nombreCursoInfo.text = curso.courseName
                    binding.descripcionCurso.text = curso.description
                    binding.tipoModalidad.text = curso.modality
                    binding.nombrePonente.text = curso.teacher
                    binding.ubicacionCurso.text = curso.address
                    //binding.fechaCurso.text = curso.startDate

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(curso.startDate)
                    val formattedDate = outputFormat.format(date)

                    binding.fechaCurso.text = formattedDate

                    if (curso.cost.toString() == "0") {
                        binding.tipoPago.text = "Gratuito"
                        val boton = findViewById<Button>(R.id.button_inscribirme)
                        boton.setText("Inscribirme")
                        boton.setOnClickListener{
                            val contenedor = findViewById<ViewGroup>(R.id.InfoCurso)
                            contenedor.removeAllViews() // Elimina todos los hijos del contenedor

                            val fragmentoNuevo = FragmentoInscripcionCurso()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                //.addToBackStack(null)
                                .commit()


                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                .addToBackStack(null)
                                .commit()


                        }

                    }
                    else{
                        binding.tipoPago.text = "$" + curso.cost.toString()
                        val boton = findViewById<Button>(R.id.button_inscribirme)
                        boton.setText("Pagar")
                        boton.setOnClickListener{
                            val contenedor = findViewById<ViewGroup>(R.id.InfoCurso)
                            contenedor.removeAllViews() // Elimina todos los hijos del contenedor

                            val fragmentoNuevo = FragmentoPagoCurso()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                //.addToBackStack(null)
                                .commit()


                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                .addToBackStack(null)
                                .commit()


                        }



                    }

                    val imageView = findViewById<ImageView>(R.id.imageView)

                    Glide.with(this@FragmentoInfoCursos)
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

