package com.example.kotlin.robertoruizapp.data.network.model.Profile

data class EditProfileRequest(
    val age: Int,
    val educationLevel: String,
    val email: String,
    val emailAgreement: Boolean,
    val gender: String,
    val job: String,
    val name: String,
    val postalCode: Int,
    val topics: List<Any>
)