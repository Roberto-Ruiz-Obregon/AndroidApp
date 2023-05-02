package com.example.kotlin.robertoruizapp.data.network.model


import com.example.kotlin.robertoruizapp.utils.Constants.BASE_URL_PROYECTO
import com.example.kotlin.robertoruizapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModuleDI {
    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/
//{{URL}}/v1/user/auth/signup
    fun getRetroInstance(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .callTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(logging)


        return Retrofit.Builder()
            .baseUrl(BASE_URL_PROYECTO)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create()
    private val okHttpClient: OkHttpClient = OkHttpClient()
    operator fun invoke(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_PROYECTO)
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(ApiService::class.java)
    }
}

