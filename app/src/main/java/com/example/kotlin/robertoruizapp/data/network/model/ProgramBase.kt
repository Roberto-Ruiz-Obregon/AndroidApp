package com.example.kotlin.robertoruizapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ProgramBase (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
        )