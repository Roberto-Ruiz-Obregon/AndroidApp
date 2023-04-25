package com.example.kotlin.robertoruizapp.data.network.model.Profile

import com.example.kotlin.robertoruizapp.data.network.model.Login.Data

data class EditProfileResponse (
    val `data`: Data,
    val status: String,
    val message: String,
    val token: String
)