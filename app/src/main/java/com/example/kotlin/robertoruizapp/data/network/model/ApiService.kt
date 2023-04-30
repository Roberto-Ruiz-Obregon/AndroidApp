package com.example.kotlin.robertoruizapp.data.network.model

import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginRequest
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.Login.User
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.data.network.model.signup.SignUp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

        @POST("user/auth/signup")
        @Headers("Accept:application/json", "Content-Type:application/json")
        fun signUpUser(@Body params: SignUp): Call<SignUp>

        //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
        @GET("course")
        suspend fun getCursos(

        ): CursosObjeto

        @GET("user/{id}")
        suspend fun getUserInfo(
            @Path("id") id: String
        ): Profile

        @GET("user/auth/me")
        suspend fun getMyInfo(
            @Header("Cookie") jwt: String
        ): Profile

        @POST("user/auth/login")
        fun postLogin(
            @Body request: LoginRequest
        ): Call<LoginResponse>

        @POST("user/auth/logout")
        fun postLogout(
            @Header("Authorization") authHeader: String
        ): Call<Void>

        @POST("inscription/inscribeTo")
        fun postInscription(
            @Header("Authorization") authHeader: String,
            @Body params: Inscription
        ): Call<Inscription>

    @Multipart
    @POST("payment/startPayment")
    fun postPago(
        @Header("Authorization") authHeader: String,
        @Part("courseId") cursoID: RequestBody?,
        @Part imagen: MultipartBody.Part

    ): Call<Pago>



}