package com.example.kotlin.robertoruizapp.model

data class Document(
    val _id: String,
    val cost: Int,
    val courseName: String,
    val createdAt: String,
    val description: String,
    val duration: Int,
    val endDate: String,
    val imageUrl: String,
    val modality: String,
    val startDate: String,
    val status: String,
    val teachers: List<String>,
    val topics: List<Any>,
    val updatedAt: String
)