package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileResponse
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {
    var editUserLiveData: MutableLiveData<EditProfileResponse?> = MutableLiveData()
    fun getProfileUserObserver(): MutableLiveData<EditProfileResponse?> {
        return editUserLiveData
    }

        fun editMyInfo(user: EditProfileRequest) {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = Repository()
                val result: EditProfileResponse? = repository.editMyInfo("Bearer ${LoginActivity.token}", user)
                Log.d("Salida", result.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    editUserLiveData.postValue(result)
                }
            }
        }

    fun deleteMyInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.deleteMyInfo("Bearer ${LoginActivity.token}")
            Log.d("Datos usuario borrado", result.toString())
        }
    }

}

