package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.data.network.model.signup.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * SignUpActivityViewModel class that manages the activity actions
 *
 */
class SignUpActivityViewModel : ViewModel() {

    var signUpNewUserLiveData: MutableLiveData<SignUp?> = MutableLiveData()

    /**
     * Gets the signUpnewUserLiveData variable
     *
     * @return MutableLiveData Object
     */
    fun getSignUpNewUserObserver(): MutableLiveData<SignUp?> {
        return signUpNewUserLiveData

    }

    /**
     * Calls the API service with the SignUp information and
     * contains the onFailure and onResponse methods.
     *
     * @param user [SignUp] Object
     */
    fun signUpNewUser(user: SignUp) {
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.signUpUser(user)

        call.enqueue(object : Callback<SignUp> {
            override fun onFailure(call: Call<SignUp>, t: Throwable) {
                signUpNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                if (response.isSuccessful) {
                    Timber.tag("Salida").d(response.toString())
                    signUpNewUserLiveData.postValue(response.body())
                } else {
                    signUpNewUserLiveData.postValue(null)
                }
            }
        })
    }

}