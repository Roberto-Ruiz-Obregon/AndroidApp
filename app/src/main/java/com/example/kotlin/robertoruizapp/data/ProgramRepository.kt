package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.ProgramAPIService
import com.example.kotlin.robertoruizapp.data.network.ProgramApiClient
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

class ProgramRepository {

    private lateinit var api: ProgramAPIService
    private val apiProgram = ProgramApiClient()

    suspend fun getProgramList(programName: String, categorySelected: String): Program? = apiProgram.getProgramList(programName, categorySelected)
    suspend fun getProgramInfo(numberProgram: String): Program? =
        apiProgram.getProgramInfo(numberProgram)
}