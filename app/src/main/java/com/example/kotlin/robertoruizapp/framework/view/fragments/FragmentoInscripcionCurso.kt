package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * FragmentoInscripcionCurso class that manages the fragment actions
 *
 */
class FragmentoInscripcionCurso : Fragment() {
    private var _binding: FragmentoInscripcionBinding? = null
    private val binding get() = _binding!!
    private var cursoID: String? = null
    private lateinit var viewModel: InscriptionViewModel

    @RequiresApi(Build.VERSION_CODES.N)

    /**
     * When the fragment is created sets up binding and viewmodel
     *
     * @param inflater How the layout wil be created
     * @param container what viewmgroup the fragment belongs to
     * @param savedInstanceState the state of the activity / fragment
     *
     * @return [View] object containing the information about the fragment
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
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

            val user = Inscription(
                cursoID
            )
            viewModel.enrollUser(token, user)

        }

        btnInscribirse.setOnClickListener {
            EnrollUser()

        }

        return root
    }

    /**
     * Gets the list of [Document] objects that contain courses information
     * to load the data into view
     *
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosNoFilter()

            CoroutineScope(Dispatchers.Main).launch {
                val curso = cursoFromID(cursoID, result)
                if (curso != null) {
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

    /**
     * Sets the binding to Null after the fragment is destoroyed
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}