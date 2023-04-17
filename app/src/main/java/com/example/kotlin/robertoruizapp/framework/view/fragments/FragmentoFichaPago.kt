package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.databinding.FragmentoFichapagoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FragmentoFichaPago : Fragment() {
    private var _binding: FragmentoFichapagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentoFichapagoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val boton : Button = root.findViewById(R.id.boton_comprobante)
        boton.setOnClickListener{
            val newFragment = FragmentoPagoCurso()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fichaPago, newFragment)
                .addToBackStack(null)
                .commit()
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}