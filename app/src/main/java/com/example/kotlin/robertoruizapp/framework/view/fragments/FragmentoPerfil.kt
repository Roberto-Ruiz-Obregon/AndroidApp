package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.databinding.FragmentoPerfilBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.EditProfileActivity
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.get
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 * FragmentoPerfil class for displaying the profile info of a user
 *
 * @return the view component of profile with the logout and edit methods
 *
 */
class FragmentoPerfil: Fragment() {
    private var _binding : FragmentoPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerfilViewModel
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this@FragmentoPerfil.requireActivity())
    }

    // creates the view of the fragment
    /**
     * When the fragment is created sets up bindinga and viewmodel
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
        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        _binding = FragmentoPerfilBinding.inflate(inflater, container, false)

        val root: View = binding.root

        initUI()

        val btnLogout = binding.btnLogout

        btnLogout.setOnClickListener {
            performLogout()
        }

        val btnEditProfile = binding.btnEditProfile

        btnEditProfile.setOnClickListener {
            editMyProfile()
        }

        val imageButton = binding.misCursosBoton

        imageButton.setOnClickListener {
            goToNewFragment()
        }

        return root
    }

    // Binds the data with the interface
    /**
     * initializes the User Interface with the data retrieved from the user login activity
     */
    private fun initUI() {
        viewModel.getMyInfo()
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

    /**
     * LogsOut the user from the application doing a POST method with Retrofit.
     * After this, the login activity is displayed
     *
     */
    // Function that performs logout of the current session
    private fun performLogout(){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val token = preferences["token", ""]
        val call = retroService.postLogout("Bearer $token")

        call.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()
                Toast.makeText(
                    this@FragmentoPerfil.requireActivity(),
                    "Logout exitoso",
                    Toast.LENGTH_SHORT
                ).show()
                passViewGoToLogin()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@FragmentoPerfil.requireActivity(),
                    "Se produjo un error en el servidor (logout)",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    /**
     * Calls the EditProfileActivity to change actual view
     *
     */
    private fun editMyProfile(){
        val intent = Intent()
        intent.setClass(requireActivity(), EditProfileActivity::class.java)
        requireActivity().startActivity(intent)
    }

    /**
     * Clears the token of the user to  nothing ("")
     *
     */
    // Clears the session
    private fun clearSessionPreference(){
        preferences["token"] = ""
    }

    /**
     * Sets the binding to Null after view is Destroyed
     *
     */
    // Destroys the view
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Calls the LoginActivity with Intent Function
     *
     */
    // Proceeds to change the actual view to the login
    private fun passViewGoToLogin() {
        val intent = Intent()
        intent.setClass(requireActivity(), LoginActivity::class.java)
        requireActivity().startActivity(intent)
    }



    private fun goToNewFragment() {

        val contenedor = (context as FragmentActivity).findViewById<ViewGroup>(R.id.frag_perfil)
        contenedor.removeAllViews()

        val fragmentoNuevo = FragmentoMisCursos()
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frag_perfil, fragmentoNuevo)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
