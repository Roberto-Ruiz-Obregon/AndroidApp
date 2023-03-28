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
import com.example.kotlin.robertoruizapp.data.network.model.ProgramBase
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentProgramasBinding
import com.example.kotlin.robertoruizapp.framework.adapters.ProgramAdapter
import com.example.kotlin.robertoruizapp.framework.viewmodel.ProgramViewModel


class ProgramFragment: Fragment() {
    private var _binding: FragmentProgramasBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProgramViewModel

    private lateinit var recyclerView: RecyclerView
    private val adapter : ProgramAdapter = ProgramAdapter()
    private lateinit var data:ArrayList<ProgramBase>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProgramViewModel::class.java]

        _binding = FragmentProgramasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        data = ArrayList()

        //todo FragmentProgramBinding
        initializeComponents(root)
        initializeObservers()
        viewModel.getProgramList()

        return root
    }

    private fun initializeComponents(root: View) {
        recyclerView = root.findViewById(R.id.rvProgramas)
    }

    private fun initializeObservers() {
        viewModel.programObjectLiveData.observe(viewLifecycleOwner) {programs ->
            setUpRecyclerView(programs)
        }
    }

    private fun setUpRecyclerView(dataForList:List<Document>){
        recyclerView.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager
            .VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        adapter.ProgramAdapter(dataForList, requireContext())
        recyclerView.adapter = adapter
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}