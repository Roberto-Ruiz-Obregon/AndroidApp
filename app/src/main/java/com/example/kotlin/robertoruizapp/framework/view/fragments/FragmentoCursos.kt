package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel
import com.example.kotlin.robertoruizapp.model.CursosObjeto
import com.example.kotlin.robertoruizapp.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentoCursos : Fragment() {

    private var _binding: FragmentoCursosBinding? = null
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
        val layoutManager = GridLayoutManager(requireContext(), 2)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclercursos)
        recyclerView.layoutManager = layoutManager
        val adapter = cursosadapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        return root
    }

    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()
            Log.d("Salida", result?.data?.documents!![0].courseName )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}