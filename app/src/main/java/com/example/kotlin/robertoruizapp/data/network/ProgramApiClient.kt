package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.data.network.model.ProgramObject

class ProgramApiClient {
    private lateinit var api: ProgramAPIService
    //todo revisar si está todo listo
    suspend fun getProgramList(limit : Int): ProgramObject? {
        api = NetworkModuleDI()
        return try {
            api.getProgramList(limit)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getProgramInfo(idProgram: Int): Program? {
        api = NetworkModuleDI()
        return try {
            api.getProgramInfo(idProgram)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

}