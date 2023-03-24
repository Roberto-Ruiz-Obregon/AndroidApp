package com.example.kotlin.robertoruizapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.signup.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivityViewModel: ViewModel() {

    var signUpNewUserLiveData: MutableLiveData<SignUp?> = MutableLiveData()

    fun getSignUpNewUserObserver(): MutableLiveData<SignUp?> {
        return signUpNewUserLiveData

    }

    fun signUpNewUser(user: SignUp){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.signUpUser(user)
        call.enqueue(object: Callback<SignUp> {
            override fun onFailure(call: Call<SignUp>, t: Throwable) {
                signUpNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                if(response.isSuccessful) {
                    Log.d("Salida", response.toString())
                    signUpNewUserLiveData.postValue(response.body())
                } else {
                    signUpNewUserLiveData.postValue(null)
                }
            }
        })
    }

}