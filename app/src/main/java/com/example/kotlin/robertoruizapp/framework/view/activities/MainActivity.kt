package com.example.kotlin.robertoruizapp.framework.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoCursos
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoHome
import com.example.kotlin.robertoruizapp.utils.Constants

class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragment: Fragment
    private var currentMenuOption:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        //initializeObservers()
        initializeListeners()
        exchangeCurrentFragment(FragmentoHome(), Constants.MENU_INICIO)
    }

    private fun initializeListeners(){
        binding.appBarMain.cursologo.setOnClickListener {
            selectMenuOption(Constants.MENU_CURSOS)
        }
        binding.appBarMain.imghome.setOnClickListener {
            selectMenuOption(Constants.MENU_INICIO)
        }
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    // conexiones
    private fun exchangeCurrentFragment(newFragment: Fragment, newMenuOption:String){
        currentFragment = newFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main,currentFragment)
            .commit()
        currentMenuOption = newMenuOption
    }

    private fun selectMenuOption(menuOption:String){
        if(menuOption == currentMenuOption){
            return
        }
        when(menuOption){
            Constants.MENU_CURSOS -> exchangeCurrentFragment(FragmentoCursos(),Constants.MENU_CURSOS)
            Constants.MENU_INICIO -> exchangeCurrentFragment(FragmentoHome(),Constants.MENU_INICIO)
            //Constants.MENU_SEARCH -> exchangeCurrentFragment(SearchFragment(),Constants.MENU_SEARCH)
        }
    }
}