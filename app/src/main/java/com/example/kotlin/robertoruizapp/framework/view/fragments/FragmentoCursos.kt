package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel

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
        val root: View = binding.root


        //initializeComponents(root)
        //initializeObservers()


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}