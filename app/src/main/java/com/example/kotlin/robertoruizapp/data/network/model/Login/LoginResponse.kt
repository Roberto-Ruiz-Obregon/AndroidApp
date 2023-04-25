package com.example.kotlin.robertoruizapp.data.network.model.Login

data class LoginResponse(
    val `data`: Data,
    val status: String,
    val message: String,
    val token: String
)