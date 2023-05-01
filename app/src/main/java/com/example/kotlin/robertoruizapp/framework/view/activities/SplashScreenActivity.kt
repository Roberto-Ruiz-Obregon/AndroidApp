package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.kotlin.robertoruizapp.databinding.ActivitySplashscreenBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.SplashScreenViewModel

/**
 * Splash screen activity that shows splash screen at the launch of the app
 *
 */
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    /**
     * Initializes the activity and calls the corresponding components
     *
     * @param savedInstanceState state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        viewModel.onCreate()
        initializeObservers()
    }

    /**
     * Initializes the binding information with the view
     *
     */
    private fun initializeBinding() {
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Initializes the Observers to change pass the view to the home fragment
     *
     */
    private fun initializeObservers() {
        viewModel.finishedLoading.observe(this, Observer { finishedLoading ->
            if (finishedLoading) {
                passViewGoToLogin()
            }
        })
    }

    /**
     * Passes the view to the home fragment as an Intent
     */
    private fun passViewGoToLogin() {
        var intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}