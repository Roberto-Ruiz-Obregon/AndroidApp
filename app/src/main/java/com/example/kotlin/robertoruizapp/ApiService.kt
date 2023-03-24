package com.example.kotlin.robertoruizapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/user/auth/signup")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun signUpUser(@Body params: User): Call<UserResponse>
}