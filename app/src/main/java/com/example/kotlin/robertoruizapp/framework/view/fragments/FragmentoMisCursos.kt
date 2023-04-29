package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.framework.adapters.miscursosadapter

class FragmentoMisCursos {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: miscursosadapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmento_mis_cursos, container, false)

        recyclerView = view.findViewById(R.id.recycler_mis_cursos)
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Aquí puedes configurar tu adaptador con la información que necesitas mostrar en el RecyclerView
        adapter = miscursosadapter()
        recyclerView.adapter = adapter

        return view
    }
}

