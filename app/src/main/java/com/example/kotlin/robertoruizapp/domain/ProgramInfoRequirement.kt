package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.ProgramRepository
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

/**
 * ProgramInfoRequirement class which has operator invoke method
 *
 */
class ProgramInfoRequirement {
    private val repository = ProgramRepository()

    /**
     * Sets the function of the invoke operator as an asynchronous function
     * that calls the getProgramInfo method from [ProgramRepository]
     *
     * @param numberProgram number also stated as the id of the program
     *
     * @return Program Type object
     */
    suspend operator fun invoke(
        numberProgram: String
    ): Program? = repository.getProgramInfo(numberProgram)
}