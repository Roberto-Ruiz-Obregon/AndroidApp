package com.example.kotlin.robertoruizapp.data.network.model.Inscripcion

data class Document(
    val topics: List<String>,
    val accessLink: String,
    val capacity: Int,
    val bank: String,
    val _id: String,
    val courseName: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val schedule: String,
    val teacher: String,
    val postalCode: String,
    val modality: String,
    val address: String,
    val status: String,
    val bankAccount: String,
    val cost: Int,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)