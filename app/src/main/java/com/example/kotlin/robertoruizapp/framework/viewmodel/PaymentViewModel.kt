package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.network.model.ApiService
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.data.network.model.NetworkModuleDI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * PaymnetViewModel that manages the activity actions
 *
 */
class PaymentViewModel : ViewModel() {

    var PaymentLiveData: MutableLiveData<Pago?> = MutableLiveData()

    fun getInscriptionObserver(): MutableLiveData<Pago?> {
        return PaymentLiveData
    }

    /**
     * Calls the API service to POST the Image of the Payment realized into the network
     * also declares the onFailure and onResponse of the API
     *
     * @param token the user token session
     * @param course the id of the course
     * @param parts [Pago] object that represents an image
     */
    fun startPayment2(token: String, course: RequestBody?, parts: RequestBody) {
        // val yo: String = "Boss"
        // val courseIdWithoutQuotes = course.trim().replace("\"","")
        val retroService = NetworkModuleDI.getRetroInstance().create(ApiService::class.java)
        val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", parts)
        Log.d("pay2", course.toString())

        val call = retroService.postPago(token, course, imagePart)



        call.enqueue(object: Callback<Pago> {
            override fun onFailure(call: Call<Pago>, t: Throwable) {
                Log.d("Falla de llamada", t.toString())
                PaymentLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Pago>, response: Response<Pago>) {
                if(response.isSuccessful) {
                    Log.d("Salida", response.toString())
                    PaymentLiveData.postValue(response.body())
                } else {
                    Log.d("Salida2", response.toString())
                    //PaymentLiveData.postValue(null)
                    PaymentLiveData.postValue(response.body())
                }
            }
        })
    }


}