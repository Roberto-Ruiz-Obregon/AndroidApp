package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentoCursos : Fragment() , CursoClickListener{

    private var _binding: FragmentoCursosBinding? = null
    private lateinit var data: List<Document>
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
        _binding = FragmentoCursosBinding.inflate(inflater, container, false)
        finishedLoading.postValue(false)
        initializeObservers()
        getCourseList()
        val root: View = binding.root
        recyclerView = root.findViewById<RecyclerView>(R.id.recyclercursos)
        progressBar = root.findViewById(R.id.pbFragmentBarraProgresoCursos)

        return root
    }

    private fun initializeObservers() {
        finishedLoading.observe(viewLifecycleOwner, Observer {finishedLoading ->
            if(finishedLoading){
                progressBarBye()
            }
        })
    }
    private fun progressBarBye() {
        progressBar?.visibility = View.GONE
    }
    private fun getCourseList(){

        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()
            Timber.tag("Salida").d(result?.data?.documents!![0].courseName)
            Timber.tag("Salida2").d(result.results.toString())

            CoroutineScope(Dispatchers.Main).launch {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoCursos
                recyclerView.layoutManager = layoutManager
                val adapter = cursosadapter(fragmentoInfoCursos)
                adapter.cursosResults(result.results)
                adapter.cursosAdapter(result.data?.documents) //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                finishedLoading.postValue(true)
            }
        }
    }

    override fun onClick(document: Document) {
        val intent = Intent(requireContext(), FragmentoInfoCursos::class.java)
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