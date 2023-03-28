package com.example.kotlin.robertoruizapp.data.network.model
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginRequest
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
    @GET("course")
    suspend fun getCursos(

    ): CursosObjeto

    @GET("user/{id}")
    suspend fun getUserInfo(
        @Path("id") id: String
    ): Profile

    @POST("user/auth/login")
    fun postLogin(
            @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("user/auth/logout")
    fun postLogout(
        @Header("Authorization") authHeader: String
    ): Call <Void>

}