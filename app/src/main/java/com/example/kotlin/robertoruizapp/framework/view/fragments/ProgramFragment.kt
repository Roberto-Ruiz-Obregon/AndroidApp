package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.databinding.FragmentProgramasBinding
import com.example.kotlin.robertoruizapp.framework.adapters.ProgramAdapter
import com.example.kotlin.robertoruizapp.framework.viewmodel.ProgramViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProgramFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentProgramasBinding? = null
    private val binding get() = _binding!!
    private var progressBar: ProgressBar? = null

    private lateinit var viewModel: ProgramViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter: ProgramAdapter = ProgramAdapter()
    private var categories: Array<String?> = arrayOf<String?>(
        "",
        "Beca", "Evento", "Apoyo", "Programa", "Otro"
    )
    private var categorySelected = ""
    private var programName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProgramViewModel::class.java]

        _binding = FragmentProgramasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar = root.findViewById(R.id.pbFragmentBarraProgreso)
        initializeComponents(root)
        initializeObservers()
        setInputs()
        getProgramList()

        return root
    }

    private fun setInputs() {
        // Spinner category
        val spinModality = binding.spinnerCategory
        spinModality.onItemSelectedListener = this
        val adModality: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.item_spinner,
            categories
        )

        adModality.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinModality.adapter = adModality

        // Name input
        binding.findProgramName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                programName = s.toString()
                Log.d("EDITTEXT", "NAME: ${programName}")
                getProgramList()
            }
        })
    }

    private fun getProgramList() {
        viewModel.getProgramList(programName, categorySelected)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        categorySelected = binding.spinnerCategory.selectedItem.toString()
        Log.d("SPINNER", "Selected: ${categorySelected}")
        getProgramList()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        getProgramList()
    }

    private fun initializeComponents(root: View) {
        recyclerView = root.findViewById(R.id.rvProgramas)

    }

    private fun initializeObservers() {
        viewModel.programObjectLiveData.observe(viewLifecycleOwner) { programs ->
            setUpRecyclerView(programs)
        }
        viewModel.finishedLoading.observe(viewLifecycleOwner) { finishedLoading ->
            if (finishedLoading) {
                progressBarBye()
            }
        }
    }

    private fun progressBarBye() {
        progressBar?.visibility = GONE
    }

    private fun setUpRecyclerView(dataForList: List<Document>) {
        recyclerView.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager
                .VERTICAL, false
        )
        recyclerView.layoutManager = gridLayoutManager
        adapter.ProgramAdapter(dataForList, requireContext())
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}