package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object that refers to Data Instance
 *
 * @constructor Create empty NetworkDI
 */
object NetworkModuleDI {
    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create()
    private val okHttpClient: OkHttpClient = OkHttpClient()

    /**
     * adds the configuration of Retrofit to the interface that represents this module
     *
     * @return [ProgramAPIService] configured
     */
    operator fun invoke(): ProgramAPIService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build()
            .create(ProgramAPIService::class.java)
    }
}