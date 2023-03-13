package com.example.kotlin.robertoruizapp

import java.util.*

data class cursos(
    val courseName: String,
    val description: String,
    val startDate: Date,
    val endDate: Date,
    val modality: String,
    val status: String,
    val imageUrl: String
) {
}