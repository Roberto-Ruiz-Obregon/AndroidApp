package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.ProgramAPIService
import com.example.kotlin.robertoruizapp.data.network.ProgramApiClient
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

/**
 * ProgramRepository class that contains the methods for Programs
 *
 */
class ProgramRepository {
    private val apiProgram = ProgramApiClient()

    /**
     * Gets the list of [Program] calling [ProgramApiClient]
     *
     * @param programName the name of the program
     * @param categorySelected category to look for
     *
     * @return [Program] object
     */
    suspend fun getProgramList(programName: String, categorySelected: String): Program? =
        apiProgram.getProgramList(programName, categorySelected)

    /**
     * Gets the [Program] information calling [ProgramApiClient]
     *
     * @param numberProgram the id of the program
     *
     * @return [Program] object
     */
    suspend fun getProgramInfo(numberProgram: String): Program? =
        apiProgram.getProgramInfo(numberProgram)
}