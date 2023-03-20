package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.UserInfo
import com.example.kotlin.robertoruizapp.domain.UserInfoRequirement
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerfilViewModel: ViewModel() {
    val userLiveData = MutableLiveData<UserInfo?>()
    private val userInfoRequirement = UserInfoRequirement()


    /*fun getUserInfo(userId: String): UserInfo? {
        return repository.getUserInfo(userId)
    }*/

    fun getUserInfo(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result : UserInfo? = userInfoRequirement(id)
            Log.d("Salida", result.toString())
            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }

    }
}