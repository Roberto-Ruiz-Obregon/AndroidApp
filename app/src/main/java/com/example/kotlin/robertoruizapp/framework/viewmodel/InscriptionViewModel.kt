package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Result
import com.example.kotlin.robertoruizapp.framework.view.activities.MainActivity
import com.example.kotlin.robertoruizapp.framework.view.fragments.FragmentoInscripcionExitosa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class InscriptionViewModel: ViewModel() {

    var InscripitionLiveData: MutableLiveData<Result?> = MutableLiveData()

    fun getInscriptionObserver(): MutableLiveData<Result?> {
        return InscripitionLiveData
    }

    fun enrollUser(token: String, course: Inscription){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.postInscription(token,course)
        call.enqueue(object: Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val inscriptionResult = response.body()
                if (response.isSuccessful) {
                    Log.d("Response" , inscriptionResult.toString())

                    if (inscriptionResult?.status == "success") {
                        Log.d("Response2", response.toString())
                        InscripitionLiveData.postValue(response.body())
                    }
                } else {
                    InscripitionLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                InscripitionLiveData.postValue(null)
            }


        })

    }

}