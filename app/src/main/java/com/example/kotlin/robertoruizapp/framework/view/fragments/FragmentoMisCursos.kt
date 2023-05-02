package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document as CourseDocument
import com.example.kotlin.robertoruizapp.data.network.model.Topic.Document as TopicsDocument
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.databinding.FragmentoMisCursosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.miscursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.InfoMisCursos
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentoMisCursos : Fragment(), CursoClickListener {
    private var _binding: FragmentoMisCursosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CursosFragmentoViewModel
    private lateinit var recyclerView: RecyclerView


     private var progressBar: ProgressBar? = null

     val finishedLoading = MutableLiveData<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CursosFragmentoViewModel::class.java]
        _binding = FragmentoMisCursosBinding.inflate(inflater, container, false)
        finishedLoading.postValue(false)
        initializeObservers()

        getMyCourseList()

        val root: View = binding.root
        Log.d("Recycler1", "Antes de llamar al recycler")
        
        recyclerView = root.findViewById(R.id.recycler_mis_cursos)




        progressBar = root.findViewById(R.id.pbFragmentBarraProgresoCursos)

        return root
    }


    private fun initializeObservers() {
        finishedLoading.observe(viewLifecycleOwner, Observer { finishedLoading ->
            if (finishedLoading) {
                progressBarBye()
            }
        })
    }

    private fun progressBarBye() {
        progressBar?.visibility = View.GONE
    }

    val token: String = "Bearer " + LoginActivity.token
    private fun getMyCourseList() {

        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getMyCourses(token)

            CoroutineScope(Dispatchers.Main).launch {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoMisCursos

                recyclerView.layoutManager = layoutManager
                val adapter = miscursosadapter(fragmentoInfoCursos)
                result!!.results?.let { adapter.miscursosResults(it) }
                result.data?.documents?.let { adapter.miscursosAdapter(it) } //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                finishedLoading.postValue(true)
            }
        }
    }

    override fun onClick(document: CourseDocument) {
        val intent = Intent(requireContext(), InfoMisCursos::class.java)
        // Imprime el valor de document._id en el Logcat
        Log.d("Salida3", "Document ID: ${document._id}")
        intent.putExtra(CURSO_ID_EXTRA, document._id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}