package com.example.kotlin.robertoruizapp

import retrofit2.http.GET

interface ApiService {

    /*
    @GET("cursos/idCurso")
    suspend fun getCursoInfo(
        @Path("idPokemon") idCurso:Int
    ): Cursos

     */

    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
    @GET("course")
    suspend fun getCursos(

    ): CursosObjeto


}

