package com.example.kotlin.robertoruizapp.model


data class Document(
    val capacity: Int,
    val _id: String,
    val cost: Int,
    val courseName: String,
    val createdAt: String,
    val updatedAt: String,
    val description: String,
    val duration: Int,
    val endDate: String,
    val imageUrl: String,
    val modality: String,
    val startDate: String,
    val status: String,
    val teacher: String,
    val topics: List<Any>,
    val postalCode: String,
    val accessLink: String,
    val address: String
)

