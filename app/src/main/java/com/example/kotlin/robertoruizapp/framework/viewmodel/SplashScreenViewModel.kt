package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * SplashScreenViewModel class that manages the activity actions
 *
 */
class SplashScreenViewModel : ViewModel() {
    val finishedLoading = MutableLiveData<Boolean>()
    fun onCreate() {
        finishedLoading.postValue(false)
        viewModelScope.launch {
            delay(Constants.SPLASHCREEN_DURATION)
            finishedLoading.postValue(true)
        }
    }
}
