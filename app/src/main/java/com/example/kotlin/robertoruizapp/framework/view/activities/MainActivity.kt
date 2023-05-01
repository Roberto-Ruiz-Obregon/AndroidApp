package com.example.kotlin.robertoruizapp.framework.view.activities


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.ActivityMainBinding
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoCursos
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoHome
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoPerfil
import com.example.kotlin.robertoruizapp.framework.view.fragments.ProgramFragment
import com.example.kotlin.robertoruizapp.utils.Constants

/**
 * MainActivity class that manages the activity actions
 *
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragment: Fragment
    private var currentMenuOption: String? = null

    /**
     * When the activity is created sets up binding and viewmodel
     * alsi initializes the manageIntent, Binding and Listener methods
     *
     * @param savedInstanceState the state of the activity / fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        initializeListeners()
        exchangeCurrentFragment(FragmentoHome(), Constants.MENU_INICIO)
    }

    /**
     * Initializes the Listeners to bind the icons with their corresponding action
     */
    private fun initializeListeners() {
        binding.appBarMain.cursologo.setOnClickListener {
            selectMenuOption(Constants.MENU_CURSOS)
        }
        binding.appBarMain.imghome.setOnClickListener {
            selectMenuOption(Constants.MENU_INICIO)
        }
        binding.appBarMain.userlogo.setOnClickListener {
            selectMenuOption(Constants.MENU_PERFIL)
        }
        binding.appBarMain.becalogo.setOnClickListener {
            selectMenuOption(Constants.MENU_PROGRAM)
        }
    }

    /**
     * Initializes the binding information of the view
     */
    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Exchanges the currentFragment into the new fragment after the menu option is selected
     *
     * @param newFragment Fragment that will set
     * @param newMenuOption name of the menu option
     */
    private fun exchangeCurrentFragment(newFragment: Fragment, newMenuOption: String) {
        currentFragment = newFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, currentFragment)
            .commit()
        currentMenuOption = newMenuOption
    }

    /**
     * Actions the change of the current Fragment after clicking on the menu option
     *
     * @param menuOption MenuOption that was selected by the user
     */
    private fun selectMenuOption(menuOption: String) {
        if (menuOption == currentMenuOption) {
            return
        }
        when (menuOption) {
            Constants.MENU_CURSOS -> exchangeCurrentFragment(
                FragmentoCursos(),
                Constants.MENU_CURSOS
            )
            Constants.MENU_INICIO -> exchangeCurrentFragment(FragmentoHome(), Constants.MENU_INICIO)
            Constants.MENU_PERFIL -> exchangeCurrentFragment(
                FragmentoPerfil(),
                Constants.MENU_PERFIL
            )
            Constants.MENU_PROGRAM -> exchangeCurrentFragment(
                ProgramFragment(), Constants
                    .MENU_PROGRAM
            )
        }
    }
}