package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoCursosRecomendados
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.*
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document as CourseDocument
import com.example.kotlin.robertoruizapp.data.network.model.Topic.Document as TopicsDocument

class FragmentoCursos : Fragment(), OnItemSelectedListener, CursoClickListener {
    private var _binding: FragmentoCursosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CursosFragmentoViewModel
    private lateinit var recyclerView: RecyclerView
    private var topics: MutableList<String> = mutableListOf<String>("")
    private var status: Array<String?> = arrayOf<String?>("", "Gratuito", "Pagado")
    private var modality: Array<String?> = arrayOf<String?>("", "Remoto", "Presencial")
    private var topicsObject: List<TopicsDocument>? = arrayListOf()
    private var topicSelected: String? = null
    private var statusSelected = ""
    private var modalitySelected = ""
    private var courseName = ""
    private var postalCode = ""
    private lateinit var currentFragment: Fragment
    private val coroutineScope = MainScope()
    private var progressBar: ProgressBar? = null
    private val finishedLoading = MutableLiveData<Boolean>()


    /**
     * When the fragment is created sets up binding, viewmodel and progress bar
     *
     * @param inflater How the layout wil be created
     * @param container what viewgroup the fragment belongs to
     * @param savedInstanceState the state of the activity / fragment
     *
     * @return [View] object containing the information about the fragment
     */
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

        val button = binding.recommendedCoursesBtn
        button.setOnClickListener{
            goToNewFragment()
        }

        setInputs()

        progressBar = root.findViewById(R.id.pbFragmentBarraProgresoCursos)

        return root
    }
    /**
     * Sets the inputs that are displayed in the view and calls the appropriate method
     * according to the situation specified
     */
    private fun setInputs() {
        // Spinner topics
        coroutineScope.launch(Dispatchers.IO) {
            val repository = Repository()
            val token: String = "Bearer " + LoginActivity.token
            val result: TopicsObject? = repository.getTopics(token)

            CoroutineScope(Dispatchers.Main).launch {
                if (isAdded) {                topicsObject = result?.data?.documents

                    result?.data?.documents?.forEach { it: TopicsDocument ->
                        topics.add(it.topic)
                    }

                    val spinTopics = binding.spinnerTopics

                    spinTopics.onItemSelectedListener = object : OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selected = binding.spinnerTopics.selectedItem.toString()

                            if (selected == "") {
                                topicSelected = null

                                getCourseList()

                                return
                            }

                            topicsObject?.forEach {
                                if (it.topic == selected) topicSelected = it._id
                            }
                            getCourseList()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            getCourseList()
                        }
                    }

                    val adTopics: ArrayAdapter<*> = ArrayAdapter<Any?>(
                        requireContext().applicationContext,
                        R.layout.item_spinner,
                        topics as List<Any?>
                    )

                    adTopics.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                    )

                    spinTopics.adapter = adTopics}

            }
        }

        // Spinner modality
        val spinModality = binding.spinnerModality
        spinModality.onItemSelectedListener = this
        val adModality: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.item_spinner,
            modality
        )

        adModality.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinModality.adapter = adModality

        // Spinner status
        val spinStatus = binding.spinnerStatus
        spinStatus.onItemSelectedListener = this
        val adStatus: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.item_spinner,
            status
        )

        adStatus.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinStatus.adapter = adStatus

        // Name input
        binding.findName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                courseName = s.toString()
                getCourseList()
            }
        })
        // Postal Code input
        binding.findCP.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                postalCode = s.toString()
                getCourseList()
            }
        })
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
        statusSelected = binding.spinnerStatus.selectedItem.toString()
        modalitySelected = binding.spinnerModality.selectedItem.toString()
        getCourseList()
    }

    /**
     * When no category is selected program list is displayed as normal
     *
     * @param parent the View of the parent
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        getCourseList()
    }

    /**
     * Initializes the Observers used in the fragment to update
     * mutable live data objects. The RecyclerView and ProgressBar
     *
     */
    private fun initializeObservers() {
        finishedLoading.observe(viewLifecycleOwner, Observer { finishedLoading ->
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
        progressBar?.visibility = View.GONE
    }

    /**
     * Gets the list of [Document] that matches the categoryselected variable
     *
     */
    private fun getCourseList() {

        coroutineScope.launch(Dispatchers.IO) {
            val repository = Repository()
            val token: String = "Bearer " + LoginActivity.token
            val result: CursosObjeto? = repository.getCursos(
                token,
                courseName,
                postalCode,
                modalitySelected,
                statusSelected,
                topicSelected
            )

            CoroutineScope(Dispatchers.Main).launch {
                if (isAdded) {
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    val fragmentoInfoCursos = this@FragmentoCursos

                    recyclerView.layoutManager = layoutManager
                    val adapter = cursosadapter(fragmentoInfoCursos)

                    adapter.cursosResults(result?.results)
                    result?.data?.documents?.let { adapter.cursosAdapter(it) } //!!
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(true)
                    finishedLoading.postValue(true)
                }

            }
        }
    }

    /**
     * Sets the interaction that will pass the view when button is clicked
     *
     * @param document gives the data to the next view
     */
    override fun onClick(document: CourseDocument) {
        val intent = Intent(requireContext(), FragmentoInfoCursos::class.java)
        // Imprime el valor de document._id en el Logcat

        intent.putExtra(CURSO_ID_EXTRA, document._id)
        startActivity(intent)
    }

    /**
     * Sets the binding to Null after the fragment is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        coroutineScope.cancel()
    }

    private fun exchangeCurrentFragment(newFragment: Fragment){
        currentFragment = newFragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main,currentFragment)
            .commit()
    }

    private fun goToNewFragment() {
        //R.id.frag_perfil es el fragmento del cual se parte
        val contenedor = (context as FragmentActivity).findViewById<ViewGroup>(R.id.TituloCursos)
        contenedor.removeAllViews()

        val fragmentoNuevo = FragmentoCursosRecomendados()
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.TituloCursos, fragmentoNuevo)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}