package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel

import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoHomeBinding

class FragmentoHome : Fragment() {

    private var _binding: FragmentoHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentoHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeListeners()
        //initializeComponents(root)
        //initializeObservers()
        return root
    }

    private fun initializeListeners() {
        binding.twitterIcon.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://twitter.com/Fundacion_RRO")
            startActivity(openURL)
        }

        binding.instaIcon.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/frobertoruizobregon/")
            startActivity(openURL)
        }

        binding.faceLogo.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.facebook.com/fundacionruizobregon/")
            startActivity(openURL)
        }

        binding.whaLogo.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://wa.me/524428205425")
            startActivity(openURL)
        }

        binding.iconollamada.setOnClickListener(){
            val phone  = "tel:" + "442  214 4020"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




