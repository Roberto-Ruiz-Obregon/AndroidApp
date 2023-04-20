package com.example.kotlin.robertoruizapp.data.network.model.Profile

data class EditProfileRequest(
    val name: String,
    val age: Int,
    val gender: String,
    val job: String,
    val educationLevel: String,
    val postalCode: Int,
)