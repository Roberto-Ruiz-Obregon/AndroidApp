package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.kotlin.robertoruizapp.framework.viewmodel.CursosFragmentoViewModel
import com.example.kotlin.robertoruizapp.utils.Constants.CURSO_ID_EXTRA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private var progressBar: ProgressBar? = null

    val finishedLoading = MutableLiveData<Boolean>()
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

    private fun setInputs() {
        // Spinner topics
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: TopicsObject? = repository.getTopics()

            CoroutineScope(Dispatchers.Main).launch {
                topicsObject = result?.data?.documents

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

                        Log.d("TOPIC", "topic: ${topicSelected}")

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

                spinTopics.adapter = adTopics
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
                Log.d("EDITTEXT", "NAME: ${courseName}")
                getCourseList()
            }
        })
        // Postal Code input
        binding.findCP.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                postalCode = s.toString()
                Log.d("EDITTEXT", "PC: ${postalCode}")
                getCourseList()
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        statusSelected = binding.spinnerStatus.selectedItem.toString()
        modalitySelected = binding.spinnerModality.selectedItem.toString()
        Log.d("SPINNER", "Selected: ${statusSelected} ${modalitySelected}")
        getCourseList()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        getCourseList()
    }

    private fun initializeObservers() {
        finishedLoading.observe(viewLifecycleOwner, Observer { finishedLoading ->
            if (finishedLoading) {
                progressBarBye()
            }
        })
    }

    private fun progressBarBye() {
        progressBar?.visibility = View.GONE
    }

    private fun getCourseList() {

        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos(
                courseName,
                postalCode,
                modalitySelected,
                statusSelected,
                topicSelected
            )

            CoroutineScope(Dispatchers.Main).launch {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoCursos

                recyclerView.layoutManager = layoutManager
                val adapter = cursosadapter(fragmentoInfoCursos)
                adapter.cursosResults(result!!.results)
                result.data?.documents?.let { adapter.cursosAdapter(it) } //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                finishedLoading.postValue(true)
            }
        }
    }

    override fun onClick(document: CourseDocument) {
        val intent = Intent(requireContext(), FragmentoInfoCursos::class.java)
        // Imprime el valor de document._id en el Logcat
        Log.d("Salida3", "Document ID: ${document._id}")


        intent.putExtra(CURSO_ID_EXTRA, document._id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
       // _binding = null
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