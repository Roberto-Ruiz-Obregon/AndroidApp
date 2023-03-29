package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilViewModel: ViewModel() {
    val userLiveData = MutableLiveData<Profile?>()
    fun getUserInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.getUserInfo(Constants.ID_PERFIL)
            Log.d("Salida", result.toString())
            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }
}