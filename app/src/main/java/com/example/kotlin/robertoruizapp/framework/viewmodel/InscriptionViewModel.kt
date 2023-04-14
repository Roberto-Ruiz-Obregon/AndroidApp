package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class InscriptionViewModel: ViewModel() {

    var InscripitionLiveData: MutableLiveData<Inscription?> = MutableLiveData()

    fun getInscriptionObserver(): MutableLiveData<Inscription?> {
        return InscripitionLiveData
    }

    fun enrollUser(user: Inscription){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.postInscription(user)
        call.enqueue(object: Callback<Inscription> {
            override fun onFailure(call: Call<Inscription>, t: Throwable) {
                InscripitionLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Inscription>, response: Response<Inscription>) {
                if(response.isSuccessful) {
                    Timber.tag("Salida").d(response.toString())
                    InscripitionLiveData.postValue(response.body())
                } else {
                    InscripitionLiveData.postValue(null)
                }
            }
        })

    }

}