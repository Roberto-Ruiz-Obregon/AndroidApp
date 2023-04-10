package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program

class ProgramApiClient {
    private lateinit var api: ProgramAPIService
    //todo revisar si está todo listo
    suspend fun getProgramList(): Program? {
        api = NetworkModuleDI()
        return try {
            api.getProgramList()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getProgramInfo(idProgram: String): Program? {
        api = NetworkModuleDI()
        return try {
            api.getProgramInfo(idProgram)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

}