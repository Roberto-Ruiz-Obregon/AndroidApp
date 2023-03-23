package com.example.kotlin.robertoruizapp.data.network.model
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //https://us-central1-robertoruiz-eca78.cloudfunctions.net/api/course/
    @GET("course")
    suspend fun getCursos(

    ): CursosObjeto

    @GET("user/{id}")
    suspend fun getUserInfo(
        @Path("id") id: String
    ): Profile

}