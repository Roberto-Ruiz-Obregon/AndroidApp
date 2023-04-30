package com.example.kotlin.robertoruizapp.data.network.model

import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Inscription
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginRequest
import com.example.kotlin.robertoruizapp.data.network.model.Login.LoginResponse
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.kotlin.robertoruizapp.data.network.model.signup.SignUp
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.PATCH
import retrofit2.http.Multipart
import retrofit2.http.Part

interface ApiService {

    @POST("user/auth/signup")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun signUpUser(@Body params: SignUp): Call<SignUp>

    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
    @GET("course")
    suspend fun getCursos(
        @Query("courseName[regex]") courseName: String,
        @Query("postalCode[regex]") postalCode: String,
        @Query("modality[regex]") modality: String,
        @Query("status[regex]") status: String,
        @Query("topics[in]") topic: String?
    ): CursosObjeto

    // Cursos Sin Filtros - HotFix
    @GET("course")
    suspend fun getCursosNoFilter() : CursosObjeto

    @GET("topics")
    suspend fun getTopics(

    ): TopicsObject

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

    // TODO: wip
    @PATCH("user/auth/updateme")
    suspend fun editMyInfo(
        @Header("Authorization") jwt: String,
        @Body request: EditProfileRequest
    ): EditProfileResponse

    @GET("user/auth/mycourses")
    suspend fun getMyCourses(
        @Header("Authorization") jwt: String
    ):CursosObjeto

    @POST("inscription/inscribeTo")
    fun postInscription(
        @Header("Authorization") authHeader: String,
        @Body params: Inscription
    ): Call<Inscription>

    @Multipart
    @POST("payment/startPayment")
    fun postPago(
        @Header("Authorization") authHeader: String,
        @Part("fieldname") fieldname: MultipartBody?,
        @Part("cursoID") cursoID: Pago
    ): Call<Pago>
}
