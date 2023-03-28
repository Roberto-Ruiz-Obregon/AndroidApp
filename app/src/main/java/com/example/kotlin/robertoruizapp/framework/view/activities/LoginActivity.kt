package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginRequest
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.databinding.ActivityLoginBinding
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.get
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["token",""].contains("."))
            goToHome()


        val btnGoMenu = findViewById<Button>(R.id.button_login)
        btnGoMenu.setOnClickListener {
            performLogin()
        }
    }

    private fun initializeBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun createSessionPreference(token: String){
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["token"] = token
    }

    private fun performLogin(){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val etEmail = findViewById<EditText>(R.id.email_login).text.toString()
        val etPassword = findViewById<EditText>(R.id.password_login).text.toString()
        val request = LoginRequest(etEmail, etPassword)
        val call = retroService.postLogin(request)

        call.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        Toast.makeText(
                                applicationContext,
                                "Se produjo un error en el servidor (null)",
                                Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                    if (loginResponse.status == "success") {
                        createSessionPreference(loginResponse.token)
                        goToHome()

                    } else {
                        Toast.makeText(
                                applicationContext,
                                "Las credenciales son incorrectas.",
                                Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    Toast.makeText(
                            applicationContext,
                            "Se produjo un error en el servidor (not success)",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                        applicationContext,
                        "Falló la llamada",
                        Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    private fun goToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


