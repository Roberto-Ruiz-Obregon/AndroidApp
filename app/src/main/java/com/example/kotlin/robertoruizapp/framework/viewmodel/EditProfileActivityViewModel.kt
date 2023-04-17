package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.data.network.model.signup.SignUp
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class EditProfileActivityViewModel : ViewModel() {
    var editProfileUserLiveData: MutableLiveData<Profile?> = MutableLiveData()
    fun getProfileUserObserver(): MutableLiveData<Profile?> {
        return editProfileUserLiveData
    }

    fun editProfileUser(user: EditProfileRequest) {
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.editMyInfo(LoginActivity.token, user)
        call.enqueue(object : Callback<SignUp> {
            override fun onFailure(call: Call<Profile>, t: Throwable) {
                editProfileUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    Timber.tag("Salida").d(response.toString())
                    editProfileUserLiveData.postValue(response.body())
                } else {
                    editProfileUserLiveData.postValue(null)
                    Timber.tag("Salida").d("Response not succesful")
                }
            }
        })
    }

}

