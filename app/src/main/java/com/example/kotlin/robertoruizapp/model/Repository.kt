package com.example.kotlin.robertoruizapp.model

class Repository() {
    private lateinit var api:ApiService

    suspend fun getCursos(): CursosObjeto?{
        api = NetworkModuleDI()
        return try{
            api.getCursos()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

}