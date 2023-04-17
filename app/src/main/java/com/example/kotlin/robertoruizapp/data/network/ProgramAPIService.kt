package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import retrofit2.http.GET
import retrofit2.http.Path

interface ProgramAPIService {
    @GET("program")
    suspend fun getProgramList(

    ): Program
    //todo Implementar el ProgramaObject

    @GET("program/{idProgram}")
    suspend fun getProgramInfo(
        @Path("idProgram") idProgram: String
    ): Program
    //todo Crar el archivo de programa
}