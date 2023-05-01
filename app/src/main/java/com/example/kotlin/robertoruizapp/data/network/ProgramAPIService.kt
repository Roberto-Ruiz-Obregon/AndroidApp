package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Creates the connection of the appto the database.
 * declaring the different methods GET
 */
interface ProgramAPIService {
    @GET("program")
    suspend fun getProgramList(
        @Query("programName[regex]") programName: String,
        @Query("category[regex]") category: String,
    ): Program

    @GET("program/{idProgram}")
    suspend fun getProgramInfo(
        @Path("idProgram") idProgram: String
    ): Program

}