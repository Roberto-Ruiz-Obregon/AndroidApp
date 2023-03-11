package com.example.kotlin.robertoruizapp

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

    /*
    suspend fun getCursoInfo(idCurso:Int): Cursos? {
        //todo: Inicializar variable api
        api = NetworkModuleDI()
        return try{
            api.getCursoInfo(idCurso)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }
    */

}