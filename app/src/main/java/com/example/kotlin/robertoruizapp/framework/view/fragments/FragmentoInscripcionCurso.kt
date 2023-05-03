package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Document as InscripcionDocument
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Data
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Result
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginRequest
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.databinding.FragmentoInscripcionBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.InscriptionViewModel
import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
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
    private lateinit var viewModelP: PerfilViewModel

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
        viewModelP = ViewModelProvider(this)[PerfilViewModel::class.java]
        _binding = FragmentoInscripcionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);

        // Carga los datos
        getCourseList()
        initUI()

        val btnInscribirse = root.findViewById<Button>(R.id.button)

        val token: String = "Bearer " + LoginActivity.token
        fun EnrollUser() {

            val user = Inscription(
                cursoID
            )
            viewModel.enrollUser(token, user)
            val confirmation = viewModel.getInscriptionObserver()

            confirmation.observe(viewLifecycleOwner){
                goToNewFragment()
                /*
                if (it?.status == "success" ) {
                    goToNewFragment()
                }
                else if (it?.message == "Ya te has inscrito a este curso.") {
                    Log.d("llamada1", it?.message)
                    makeToast("Ya estás inscrito a este curso")
                } else if (it?.message == "Este curso ya ha iniciado, no puedes inscribirte.") {
                    Log.d("llamada2", it?.message)
                    makeToast("Este curso ya ha iniciado")
                } else if (it?.message == "No haz iniciado sesion, por favor inicia sesion para obtener acceso.") {
                    Log.d("llamada3", it?.message)
                    makeToast("No haz iniciado sesión, por favor inicia sesión para poder inscribirte")
                }else if (it?.message == " Ya no hay espacio disponible en este curso.") {
                    Log.d("llamada4", it?.message)
                    makeToast("Ya no hay espacio disponible en este curso.")
                }
                else if (it?.message == "Hemos tenido problemas enviando un correo de confirmación.") {
                    Log.d("llamada5", it?.message)
                    makeToast("Hemos tenido problemas enviando un correo de confirmación")
                    goToNewFragment()
                } else{
                    Log.d("llamada6", it?.message.toString())
                    Log.d("llamada7", it?.status.toString())
                    makeToast("Error al inscribirse")
                }*/

            }
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
    val token: String = "Bearer " + LoginActivity.token
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCourseList() {
        CoroutineScope(Dispatchers.IO).launch {

            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosNoFilter(token)

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

    private fun initUI() {
        viewModelP.getMyInfo()
        viewModelP.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.piNombre.text = user.data.document.name.toString()
                binding.piEdad.text = user.data.document.age.toString()
                binding.piSexo.text = user.data.document.gender
                binding.piCorreo.text = user.data.document.email
                binding.piAcademico.text = user.data.document.educationLevel
                binding.piOcupacion.text = user.data.document.job
            }
        }
    }

    private fun goToNewFragment() {

        val contenedor = (context as FragmentActivity).findViewById<ViewGroup>(R.id.Inscripcion)
        contenedor.removeAllViews()

        val fragmentoNuevo = FragmentoInscripcionExitosa()
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.Inscripcion, fragmentoNuevo)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun Fragment.makeToast(text: String,duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
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