package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import java.io.File

/**
 * LoginActivity class that manages the activity actions
 *
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object UserToken {
        var token: String = ""
    }

    fun isFileExists(file: File): Boolean {
        return file.exists() && !file.isDirectory
    }

    /**
     * When the activity is created sets up binding and viewmodel
     *
     * @param savedInstanceState the state of the activity / fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["token", ""].contains("."))
            goToHome()

        val btnGoMenu = findViewById<Button>(R.id.button_login)
        btnGoMenu.setOnClickListener {
            performLogin()
        }

        val btnStartRegisterActivity = findViewById<Button>(R.id.signup)

        btnStartRegisterActivity.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }

    /**
     * Initializes the binding information of the view
     */
    private fun initializeBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Creates the preferences of the seesion indicating what is the tokenSession
     *
     * @param token Session of the current user
     */
    private fun createSessionPreference(token: String) {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["token"] = token
    }

    /**
     * POST the login information of the user to pass to the home section
     */
    private fun performLogin() {
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val etEmail = findViewById<EditText>(R.id.email_login).text.toString()
        val etPassword = findViewById<EditText>(R.id.password_login).text.toString()
        val request = LoginRequest(etEmail, etPassword)
        val call = retroService.postLogin(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    if (loginResponse?.status == "success") {
//                        checkCache(loginResponse.token)
                        token = loginResponse.token
                        createSessionPreference(loginResponse.token)
                        goToHome()

                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Usuario o contraseña no válidos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            /**
             * Function that displays a toast when the call to backend fails
             *
             * @param call the call that was created
             * @param t exception to be thrown
             */
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "No se pudo conectar a servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    /**
     * TODO
     *
     */
    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}


