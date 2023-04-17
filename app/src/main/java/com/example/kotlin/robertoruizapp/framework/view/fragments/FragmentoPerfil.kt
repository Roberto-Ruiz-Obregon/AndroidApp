package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.databinding.FragmentoPerfilBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PerfilViewModel
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.get
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoPerfil: Fragment() {
    private var _binding : FragmentoPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerfilViewModel
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this@FragmentoPerfil.requireActivity())
    }

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

        return root
    }

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
    // TODO
    private fun editMyProfile(){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val token = preferences["token", ""]
        val call = retroService.editMyInfo("Bearer $token")
    }

    private fun clearSessionPreference(){
        preferences["token"] = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun passViewGoToLogin() {
        val intent = Intent()
        intent.setClass(requireActivity(), LoginActivity::class.java)
        requireActivity().startActivity(intent)
    }

}
