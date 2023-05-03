package com.example.kotlin.robertoruizapp.data.network.model.program

data class Document(
    val _id: String,
    val programName: String,
    val imageUrl: String,
    val description: String,
    val category: String,
    val hasLimit: String,
    val limitDate: String,
    val createdAt: String,
    val updatedAt: String
)


