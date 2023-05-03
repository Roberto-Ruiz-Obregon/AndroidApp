package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosRecomendadosBinding
import com.example.kotlin.robertoruizapp.framework.adapters.cursosadapter
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentoCursosRecomendados : Fragment(),
    CursoClickListener {

    private var _binding: FragmentoCursosRecomendadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerfilViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        _binding = FragmentoCursosRecomendadosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = root.findViewById(R.id.recyclercursosrecomendados)
        initUserData()
        return root
    }

    private fun initUserData() {
        viewModel.getMyInfo()
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Log.d("postal code", user.toString() )
                val postalCode: String = user.data.document.postalCode.toString()
                getCourseList(postalCode)
            }
        }
    }

    private fun getCourseList(postalCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursosRecomendados(postalCode)
            for (it in result?.data?.documents!!) {
                Log.d("getCourseList", it.toString())
            }
            CoroutineScope(Dispatchers.Main).launch {
                val layoutManager = GridLayoutManager(requireContext(), 2)
                val fragmentoInfoCursos = this@FragmentoCursosRecomendados

                recyclerView.layoutManager = layoutManager
                val adapter = cursosadapter(fragmentoInfoCursos)
                adapter.cursosResults(result!!.results)
                result.data?.documents?.let { adapter.cursosAdapter(it) } //!!
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)

            }
        }
    }

    override fun onClick(document: Document) {
        val intent = Intent(requireContext(), FragmentoInfoCursos::class.java)
        // Imprime el valor de document._id en el Logcat
        Log.d("Salida3", "Document ID: ${document._id}")


        intent.putExtra(Constants.CURSO_ID_EXTRA, document._id)
        startActivity(intent)
    }
}
