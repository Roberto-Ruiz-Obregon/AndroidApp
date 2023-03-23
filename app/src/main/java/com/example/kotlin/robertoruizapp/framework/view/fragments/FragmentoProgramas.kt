package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoProgramasBinding

import com.example.kotlin.robertoruizapp.framework.adapters.programasAdapter
import com.example.kotlin.robertoruizapp.framework.viewmodel.ProgramasFragmentoViewModel

class FragmentoProgramas : Fragment() {

    private var _binding: FragmentoProgramasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProgramasFragmentoViewModel
    private lateinit var recyclerView: RecyclerView

    // Vista de inicializacion
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProgramasFragmentoViewModel::class.java]
        _binding = FragmentoProgramasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Como se maneja la vista, above es cosa de Kotlin
        val layoutManager = GridLayoutManager(requireContext(), 2)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rvProgramas)
        recyclerView.layoutManager = layoutManager
        val adapter = programasAdapter()

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}