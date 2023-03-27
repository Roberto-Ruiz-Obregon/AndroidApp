package com.example.kotlin.robertoruizapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ProgramObject (
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: ArrayList<ProgramBase>,
)