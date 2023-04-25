package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.ProgramRepository
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

/**
 * ProgramListRequirement class which has operator invoke method
 *
 */
class ProgramListRequirement {

    private val repository = ProgramRepository()

    /**
     *  Sets the function of the invoke operator as an asynchronous function
     *  that calls the getProgramList method from [ProgramRepository]
     *
     * @param programName name of the program
     * @param categorySelected category selected by the user
     *
     * @return Program Type object
     */
    suspend operator fun invoke(
        programName: String, categorySelected: String
    ): Program? = repository.getProgramList(programName, categorySelected)
}