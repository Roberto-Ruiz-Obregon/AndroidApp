package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.data.network.model.UserInfo
import com.example.kotlin.robertoruizapp.databinding.FragmentoPerfilBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel


class FragmentoPerfil: Fragment() {
    private var _binding : FragmentoPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerfilViewModel
    private lateinit var data: ArrayList<UserInfo>
    lateinit var context: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        _binding = FragmentoPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        data = ArrayList()

        val userId = "6405a58bb5883551fc92fdb2"

        initializeObservers()
        viewModel.getUserInfo(userId)



        return root
    }

    private fun initializeObservers() {
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            if (user != null){
                binding.PNombre.text = user.name
                binding.PEdad.text = user.age.toString()
                binding.PSexo.text = user.gender
                binding.PCorreo.text = user.email
                binding.PNivel.text = user.educationLevel
                binding.POcupacion.text = user.job
                binding.PCp.text = user.postalCode.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}