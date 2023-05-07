package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentProgramasBinding
import com.example.kotlin.robertoruizapp.framework.adapters.ProgramAdapter
import com.example.kotlin.robertoruizapp.framework.viewmodel.ProgramViewModel
import kotlinx.coroutines.MainScope



/**
 * ProgramFragment class that manages the fragment actions
 */
class ProgramFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentProgramasBinding? = null
    private val binding get() = _binding!!
    private var progressBar: ProgressBar? = null

    private lateinit var viewModel: ProgramViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter: ProgramAdapter = ProgramAdapter()
    private var categorySelected = ""
    private var programName = ""
    private var categories: Array<String?> = arrayOf<String?>(
        "",
        "Beca", "Evento", "Apoyo", "Programa", "Otro"
    )

    /**
     * When the fragment is created sets up binding, viewmodel and progress bar
     *
     * @param inflater How the layout wil be created
     * @param container what viewmgroup the fragment belongs to
     * @param savedInstanceState the state of the activity / fragment
     *
     * @return [View] object containing the information about the fragment
     */
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

    /**
     * Sets the inputs that are displayed in the view and calls the appropriate method
     * according to the situation specified
     */
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

        binding.findProgramName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                programName = s.toString()
                getProgramList()
            }
        })
    }

    /**
     * Gets the list of [Program] that matches the categoryselected variable
     *
     */
    private fun getProgramList() {
        viewModel.getProgramList(programName, categorySelected)
    }

    /**
     * When an Item is selected in the spinner, the category selected variable is updated
     * and calls the getProgramList() method
     *
     * @param parent AdapterView of the parent
     * @param view View of the parent
     * @param position an Integer
     * @param id id of the item
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        categorySelected = binding.spinnerCategory.selectedItem.toString()
        getProgramList()
    }

    /**
     * When no category is selected program list is displayed as normal
     *
     * @param parent the View of the parent
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        getProgramList()
    }

    /**
     * Initializes the components used in the fragment, in this case is the [RecyclerView]
     *
     * @param root the root [View]
     */
    private fun initializeComponents(root: View) {
        recyclerView = root.findViewById(R.id.rvProgramas)

    }

    /**
     * Initializes the Observers used in the fragment to update
     * mutable live data objects. The RecyclerView and ProgressBar
     *
     */
    private fun initializeObservers() {
        viewModel.programObjectLiveData.observe(viewLifecycleOwner, { programs ->
            setUpRecyclerView(programs)
        })
        viewModel.finishedLoading.observe(viewLifecycleOwner, { finishedLoading ->
            if (finishedLoading) {
                progressBarBye()
            }
        })
    }



    /**
     * Changes the display of the ProgessBar to GONE state
     *
     */
    private fun progressBarBye() {
        progressBar?.visibility = GONE
    }

    /**
     * Sets the layout of the [RecyclerView] into the Fragment and
     * receives the data that will be displayed
     *
     * @param dataForList list of [Document] objects
     */
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

    /**
     * Sets the binding to Null after the fragment is destoroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}