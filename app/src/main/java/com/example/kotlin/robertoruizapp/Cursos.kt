package com.example.kotlin.robertoruizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoCursosBinding

class Cursos : Fragment() {
    private lateinit var binding: FragmentoCursosBinding
    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = FragmentoCursosBinding.inflate(
             inflater, container, false
         )

        return binding.root
    }
}