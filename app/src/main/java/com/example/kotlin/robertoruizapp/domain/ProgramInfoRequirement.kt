package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.ProgramRepository

class ProgramInfoRequirement {
    private val repository = ProgramRepository()

    suspend operator fun invoke(
        numberProgram: Int
    ): Program? = repository.getProgramInfo(numberProgram)
}