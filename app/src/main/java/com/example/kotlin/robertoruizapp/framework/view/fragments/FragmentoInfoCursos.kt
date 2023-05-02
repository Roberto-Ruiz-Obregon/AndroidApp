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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * FragmentoInforCursos class that manages the fragment actions
 */
class FragmentoInfoCursos : AppCompatActivity() {
    private lateinit var binding: FragmentoInfoCursosBinding
    private var cursoID: String? = null
    private lateinit var currentFragment: Fragment

    /**
     * Sets the information for the current fragment when creating the view
     *
     * @param savedInstanceState the state of the view
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        cursoID = intent.getStringExtra(CURSO_ID_EXTRA)

        // Carga los datos
        lateinit var data: List<Document>
        cursoID = intent.getStringExtra(CURSO_ID_EXTRA)

        initializeBinding()
        getCourseList()

    }

    /**
     * Initializes the components of the view to set up binding
     *
     */
    private fun initializeBinding() {
        binding = FragmentoInfoCursosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Calls the [Repository] to get a list of [Document] which are courses.
     * Also binds the information to the view
     *
     */
    val token: String = "Bearer " + LoginActivity.token
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosNoFilter(token)

            CoroutineScope(Dispatchers.Main).launch {
                val curso = cursoFromID(cursoID, result)
                if (curso != null) {
                    binding.nombreCursoInfo.text = curso.courseName
                    binding.descripcionCurso.text = curso.description
                    binding.tipoModalidad.text = curso.modality
                    binding.nombrePonente.text = curso.teacher
                    binding.horario.text = curso.schedule

                    if (curso.modality == "Remoto"){
                        binding.ubicacionCurso.text = "Curso Online"
                        /*
                        Para que cargue el link del zoom se usa esta linea (Se pone despues de que un usuario se inscribe)
                        binding.ubicacionCurso.text = curso.accessLink
                        */
                    }
                    else{
                        binding.ubicacionCurso.text = curso.address
                    }
                    //binding.fechaCurso.text = curso.startDate

                    val inputFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(curso.startDate)
                    val enddate = inputFormat.parse(curso.endDate)
                    val formattedDate = outputFormat.format(date)
                    val endformattedDate = outputFormat.format(enddate)

                    binding.fechaCurso.text = formattedDate
                    binding.fechafin.text = endformattedDate

                    if (curso.cost.toString() == "0") {
                        binding.tipoPago.text = "Gratuito"
                        val boton = findViewById<Button>(R.id.button_inscribirme)
                        boton.setText("Inscribirme")
                        boton.setOnClickListener {
                            val contenedor = findViewById<ViewGroup>(R.id.InfoCurso)
                            contenedor.removeAllViews() // Elimina todos los hijos del contenedor

                            val fragmentoNuevo = FragmentoInscripcionCurso()
                            supportFragmentManager.beginTransaction()

                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                .commit()


                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
                                .addToBackStack(null)
                                .commit()


                        }

                    } else {
                        binding.tipoPago.text = "$" + curso.cost.toString()
                        val boton = findViewById<Button>(R.id.button_inscribirme)
                        boton.setText("Pagar")
                        boton.setOnClickListener {
                            val contenedor = findViewById<ViewGroup>(R.id.InfoCurso)
                            contenedor.removeAllViews() // Elimina todos los hijos del contenedor

                            val fragmentoNuevo = FragmentoFichaPago()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.InfoCurso, fragmentoNuevo)
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

    /**
     * Gets the [Document] object that matches the [cursoID] and [result] params
     *
     * @param cursoID the id of the curso
     * @param result the information retrieved from API response
     *
     * @return the [Document] object that matches the [curso] or Null
     */
    private fun cursoFromID(cursoID: String?, result: CursosObjeto?): Document? {

        for (curso in result!!.data.documents) {
            var cursoid = curso._id
            if (cursoid.toString() == cursoID)
                return curso
        }
        return null
    }
}

