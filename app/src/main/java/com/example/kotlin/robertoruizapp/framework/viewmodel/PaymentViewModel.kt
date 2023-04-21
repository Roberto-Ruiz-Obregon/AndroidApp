package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class PaymentViewModel: ViewModel() {

    var PaymentLiveData: MutableLiveData<Pago?> = MutableLiveData()

    fun getInscriptionObserver(): MutableLiveData<Pago?> {
        return PaymentLiveData
    }

    fun startPayment(token: String, course: Pago){
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val call = retroService.postPago(token,course)
        call.enqueue(object: Callback<Pago> {
            override fun onFailure(call: Call<Pago>, t: Throwable) {
                Log.d("Falla de llamada", t.toString())
                PaymentLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Pago>, response: Response<Pago>) {
                if(response.isSuccessful) {
                    Timber.tag("Salida").d(response.toString())
                    PaymentLiveData.postValue(response.body())
                } else {
                    PaymentLiveData.postValue(null)
                }
            }
        })

    }

}