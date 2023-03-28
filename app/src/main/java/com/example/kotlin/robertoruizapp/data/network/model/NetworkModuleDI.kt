package com.example.kotlin.robertoruizapp.data.network.model


import com.example.kotlin.robertoruizapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModuleDI {
    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create()
    private val okHttpClient: OkHttpClient = OkHttpClient()
    operator fun invoke(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_PROYECTO)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getRetroInstance(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_PROYECTO)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}