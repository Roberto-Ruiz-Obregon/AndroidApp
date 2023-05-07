package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
    private var progressBar: ProgressBar? = null

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


     /*   val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["token", ""].contains("."))
            goToHome()*/

        val loadingPanel = findViewById<RelativeLayout>(R.id.loadingPanel)
        progressBar = findViewById(R.id.progressBarLogin)
        val btnGoMenu = findViewById<Button>(R.id.button_login)
        val btnSign = findViewById<Button>(R.id.signup)
        val emailLogin = findViewById<TextView>(R.id.email_login)
        val passwordLogin = findViewById<TextView>(R.id.password_login)
        btnGoMenu.setOnClickListener {
            loadingPanel.visibility = View.VISIBLE
            progressBar?.visibility = View.VISIBLE
            passwordLogin.isEnabled = false
            emailLogin.isEnabled = false
            btnGoMenu.isEnabled = false
            btnSign.isEnabled = false
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
        val loadingPanel = findViewById<RelativeLayout>(R.id.loadingPanel)
        val btnGoMenu = findViewById<Button>(R.id.button_login)
        val btnSign = findViewById<Button>(R.id.signup)
        val emailLogin = findViewById<TextView>(R.id.email_login)
        val passwordLogin = findViewById<TextView>(R.id.password_login)


        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (response.isSuccessful) {
                    if (loginResponse?.status == "success") {
//                        checkCache(loginResponse.token)
                        token = loginResponse.token
                        createSessionPreference(loginResponse.token)
                        progressBar?.visibility = View.INVISIBLE
                        goToHome()

                    }
                } else {
                    progressBar?.visibility = View.INVISIBLE
                    loadingPanel.visibility = View.INVISIBLE
                    passwordLogin.isEnabled = true
                    emailLogin.isEnabled = true
                    btnGoMenu.isEnabled = true
                    btnSign.isEnabled = true

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
                progressBar?.visibility = View.INVISIBLE
                loadingPanel.visibility = View.INVISIBLE
                passwordLogin.isEnabled = true
                emailLogin.isEnabled = true
                btnGoMenu.isEnabled = true
                btnSign.isEnabled = true
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


