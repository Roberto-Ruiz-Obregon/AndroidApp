package com.example.kotlin.robertoruizapp.signup

data class SignUp(
    val age: Int,
    val educationLevel: String,
    val email: String,
    val gender: String,
    val job: String,
    val name: String,
    val password: String,
    val passwordConfirm: String,
    val postalCode: Int
)