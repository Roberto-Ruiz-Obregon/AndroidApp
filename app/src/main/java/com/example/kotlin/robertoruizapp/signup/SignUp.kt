package com.example.kotlin.robertoruizapp.signup

data class SignUp(
    val name: String,
    val age: Int,
    val gender: String,
    val job: String,
    val educationLevel: String,
    val postalCode: Int,
    val email: String,
    val password: String,
    val passwordConfirm: String,
)