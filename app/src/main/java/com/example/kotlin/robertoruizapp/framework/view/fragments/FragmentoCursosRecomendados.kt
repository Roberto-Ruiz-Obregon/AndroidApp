package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosRecomendadosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentoCursosRecomendados : Fragment(), AdapterView.OnItemSelectedListener,
    CursoClickListener {

    private var _binding: FragmentoCursosRecomendadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var postalCode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = binding.root
        recyclerView = root.findViewById(R.id.cursos_recomendados)
        getCourseList()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(document: Document) {
        TODO("Not yet implemented")
    }

    private fun getCourseList() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosRecomendados(postalCode)

            CoroutineScope(Dispatchers.Main).launch {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoCursosRecomendados

                recyclerView.layoutManager = layoutManager
                val adapter = cursosadapter(fragmentoInfoCursos)
                adapter.cursosResults(result!!.results)
                result.data?.documents?.let { adapter.cursosAdapter(it) } //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
            }
        }
    }
}