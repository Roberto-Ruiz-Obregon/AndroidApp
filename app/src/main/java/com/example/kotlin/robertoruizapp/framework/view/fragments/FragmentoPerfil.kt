package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.databinding.FragmentoPerfilBinding

import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel


class FragmentoPerfil: Fragment() {
    private var _binding : FragmentoPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerfilViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        _binding = FragmentoPerfilBinding.inflate(inflater, container, false)

        val root: View = binding.root

        initUI()

        return root
    }

    private fun initUI() {
        viewModel.getUserInfo()
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.PNombre.text = user.data.document.name.toString()
                binding.PEdad.text = user.data.document.age.toString()
                binding.PSexo.text = user.data.document.gender
                binding.PCorreo.text = user.data.document.email
                binding.PNivel.text = user.data.document.educationLevel
                binding.POcupacion.text = user.data.document.job
                binding.PCp.text = user.data.document.postalCode.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}