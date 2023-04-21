package com.example.kotlin.robertoruizapp.data.network.model.Inscripcion

import okhttp3.MultipartBody

data class  Pago(
    val courseId: String?,
    //val status: String,
    val billImageUrl: MultipartBody.Part
)