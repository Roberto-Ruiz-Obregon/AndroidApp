package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.databinding.FragmentoHomeBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.get
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoPerfil as perfil
/**
 * FragmentHome class that manages the fragment actions
 */
class FragmentoHome : Fragment() {

    private var _binding: FragmentoHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this@FragmentoHome.requireActivity())
    }

    /**
     * When the fragment is created sets up binding, viewmodel and progress bar
     *
     * @param inflater How the layout wil be created
     * @param container what viewmgroup the fragment belongs to
     * @param savedInstanceState the state of the activity / fragment
     *
     * @return [View] object containing the information about the fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentoHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeListeners()

        val imageView = binding.fondoImagen

        val configuration = binding.config
        configuration.setOnClickListener {
            val popupMenu = PopupMenu(context, configuration)
            popupMenu.menuInflater.inflate(R.menu.configuration_button, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item1 -> {
                        performLogout()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }


        imageView.setOnClickListener {
            goToNewFragment()
        }
        return root
    }

    /**
     * Initializes the listeners for the buttons in the home view
     *
     */
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
            openURL.data = Uri.parse("https://wa.me/524422142380")
            startActivity(openURL)
        }

        binding.telefono1.setOnClickListener(){
            val phone  = "tel:" + "442  214 4020"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))

        }

        binding.telefono2.setOnClickListener() {
            val phone = "tel:" + "442  214 2380"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))

        }
        binding.telefono3.setOnClickListener(){
            val phone  = "tel:" + "442  214 3033"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))

        }
    }

    private fun performLogout(){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val token = preferences["token", ""]
        val call = retroService.postLogout("Bearer $token")

        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()
                Toast.makeText(
                    this@FragmentoHome.requireActivity(),
                    "Logout exitoso",
                    Toast.LENGTH_SHORT
                ).show()
                passViewGoToLogin()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@FragmentoHome.requireActivity(),
                    "Se produjo un error en el servidor (logout)",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
    private fun clearSessionPreference(){
        preferences["token"] = ""
    }
    private fun passViewGoToLogin() {
        val intent = Intent()
        intent.setClass(requireActivity(), LoginActivity::class.java)
        requireActivity().startActivity(intent)
    }

    private fun goToNewFragment() {

        val contenedor = (context as FragmentActivity).findViewById<ViewGroup>(R.id.frag_home)
        contenedor.removeAllViews()

        val fragmentoNuevo = FragmentoMisCursos()
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frag_home, fragmentoNuevo)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    /**
     * Sets the binding to Null after the fragment is destoroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}




