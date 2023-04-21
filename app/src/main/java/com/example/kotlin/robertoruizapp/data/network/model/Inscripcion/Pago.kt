package com.example.kotlin.robertoruizapp.data.network.model.Inscripcion

import android.net.Uri

data class  Pago (
    val courseId: String?,
    val status: String,
    val billImageUrl: Uri?
)