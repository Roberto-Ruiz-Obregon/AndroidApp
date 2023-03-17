package com.example.kotlin.robertoruizapp.model
import retrofit2.http.GET

interface ApiService {

    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
    @GET("course")
    suspend fun getCursos(

    ): CursosObjeto


}