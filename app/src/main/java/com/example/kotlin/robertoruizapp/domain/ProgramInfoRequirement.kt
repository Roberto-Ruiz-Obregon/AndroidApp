package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.ProgramRepository
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

class ProgramInfoRequirement {
    private val repository = ProgramRepository()

    suspend operator fun invoke(
        numberProgram: String
    ): Document? = repository.getProgramInfo(numberProgram)
}