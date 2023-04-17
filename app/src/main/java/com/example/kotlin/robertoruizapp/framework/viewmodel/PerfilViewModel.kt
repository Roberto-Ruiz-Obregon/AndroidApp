package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity.UserToken.token
import com.example.kotlin.robertoruizapp.utils.Constants
import com.example.kotlin.robertoruizapp.utils.PreferenceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PerfilViewModel : ViewModel() {
    val userLiveData = MutableLiveData<Profile?>()
    fun getUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.getUserInfo(Constants.ID_PERFIL)
            Log.d("Salida", result.toString())
            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }

    // TODO add token to cache
    fun getMyInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.getMyInfo("jwt=${token}")
            Log.d("Salida", result.toString())
            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }

    fun editMyInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.editMyInfo("jwt=${token}", EditProfileRequest)
            Log.d("Salida", result.toString())
            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }

}