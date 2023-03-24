package com.example.kotlin.robertoruizapp

import com.example.kotlin.robertoruizapp.signup.SignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("user/auth/signup")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun signUpUser(@Body params: SignUp): Call<SignUp>
}