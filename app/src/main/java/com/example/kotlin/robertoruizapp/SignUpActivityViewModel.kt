package com.example.kotlin.robertoruizapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivityViewModel: ViewModel() {

    var signUpNewUserLiveData: MutableLiveData<UserResponse?> = MutableLiveData()

    fun getSignUpNewUserObserver(): MutableLiveData<UserResponse?> {
        return signUpNewUserLiveData

    }

    fun signUpNewUser(user: User){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.signUpUser(user)
        call.enqueue(object: Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                signUpNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful) {
                    signUpNewUserLiveData.postValue(response.body())
                } else {
                    signUpNewUserLiveData.postValue(null)
                }
            }
        })
    }

}