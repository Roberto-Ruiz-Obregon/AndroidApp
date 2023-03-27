package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.ProgramRepository
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

class ProgramListRequirement {

    private val repository = ProgramRepository()

    suspend operator fun invoke(
        limit: Int
    ): Program? = repository.getProgramList(limit)
}