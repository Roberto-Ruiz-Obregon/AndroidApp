package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.ProgramAPIService
import com.example.kotlin.robertoruizapp.data.network.ProgramApiClient
import com.example.kotlin.robertoruizapp.data.network.model.ProgramObject

class ProgramRepository {

    private lateinit var api: ProgramAPIService
    private val apiProgram = ProgramApiClient()

    suspend fun getProgramList(limit: Int): ProgramObject? = apiProgram.getProgramList(limit)
    suspend fun getProgramInfo(numberProgram: Int): Program? =
        apiProgram.getProgramInfo(numberProgram)
}