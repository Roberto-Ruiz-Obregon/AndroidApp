package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * InscriptionViewModel that manages the activity actions
 *
 */
class InscriptionViewModel : ViewModel() {

    var InscripitionLiveData: MutableLiveData<Inscription?> = MutableLiveData()

    fun getInscriptionObserver(): MutableLiveData<Inscription?> {
        return InscripitionLiveData
    }

    /**
     *  Calls the API service to POST the inscription of the user logged in the app
     *  and manages the onFailure and onResponse methods
     *
     * @param token the user token session
     * @param course the id of the course
     */
    fun enrollUser(token: String, course: Inscription) {
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.postInscription(token, course)
        call.enqueue(object : Callback<Inscription> {
            override fun onFailure(call: Call<Inscription>, t: Throwable) {

                InscripitionLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Inscription>, response: Response<Inscription>) {
                if (response.isSuccessful) {
                    Timber.tag("Salida").d(response.toString())
                    InscripitionLiveData.postValue(response.body())
                } else {
                    InscripitionLiveData.postValue(null)
                }
            }
        })

    }

}