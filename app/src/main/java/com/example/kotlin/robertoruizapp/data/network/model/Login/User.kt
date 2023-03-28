package com.example.kotlin.robertoruizapp.data.network.model.Login

data class User(
    val __v: Int,
    val _id: String,
    val age: Int,
    val educationLevel: String,
    val email: String,
    val emailAgreement: Boolean,
    val gender: String,
    val job: String,
    val name: String,
    val postalCode: Int,
    val topics: List<String>
)