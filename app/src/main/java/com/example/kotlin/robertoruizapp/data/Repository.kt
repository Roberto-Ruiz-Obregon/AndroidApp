package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.model.*
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile

class Repository() {
    private lateinit var api: ApiService

    suspend fun getCursos(courseName: String, postalCode: String, modality: String, status: String, topic: String?): CursosObjeto?{
        api = NetworkModuleDI()
        return try{
            api.getCursos(courseName, postalCode, modality, status, topic)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getTopics(): TopicsObject?{
        api = NetworkModuleDI()
        return try{
            api.getTopics()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getUserInfo(id:String): Profile?{
        api = NetworkModuleDI()
        return try{
            api.getUserInfo(id)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getMyInfo(jwt:String): Profile?{
        api = NetworkModuleDI()
        return try{
            api.getMyInfo(jwt)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

   // suspend fun getUserInfo(id:String): UserInfo? = getUser(id)

}