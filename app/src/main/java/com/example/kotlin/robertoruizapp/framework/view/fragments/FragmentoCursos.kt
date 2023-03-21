package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel
import com.example.kotlin.robertoruizapp.model.CURSO_ID_EXTRA
import com.example.kotlin.robertoruizapp.model.CursosObjeto
import com.example.kotlin.robertoruizapp.model.Document
import com.example.kotlin.robertoruizapp.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentoCursos : Fragment() , CursoClickListener{

    private var _binding: FragmentoCursosBinding? = null
    private lateinit var data: List<Document>
    private val binding get() = _binding!!
    private lateinit var viewModel: CursosFragmentoViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CursosFragmentoViewModel::class.java]
        _binding = FragmentoCursosBinding.inflate(inflater, container, false)
        getCourseList()
        val root: View = binding.root
        recyclerView = root.findViewById<RecyclerView>(R.id.recyclercursos)
        return root
    }

    override fun onClick(document: Document) {
        val intent = Intent(requireContext(), FragmentoInfoCursos::class.java)
        intent.putExtra(CURSO_ID_EXTRA, document._id)
        startActivity(intent)
    }

    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()
            Log.d("Salida", result?.data?.documents!![0].courseName )
            Log.d("Salida2", result.results.toString())
            CoroutineScope(Dispatchers.Main).launch{
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoCursos
                recyclerView.layoutManager = layoutManager
                val adapter = cursosadapter(fragmentoInfoCursos)
                adapter.cursosResults(result.results)
                adapter.cursosAdapter(result.data?.documents) //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}