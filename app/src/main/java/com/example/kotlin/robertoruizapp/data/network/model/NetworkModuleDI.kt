package com.example.kotlin.robertoruizapp.data.network.model


import com.example.kotlin.robertoruizapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModuleDI {
    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create()
    private val okHttpClient: OkHttpClient = OkHttpClient()
    operator fun invoke(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_PROYECTO)
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(ApiService::class.java)
    }

}