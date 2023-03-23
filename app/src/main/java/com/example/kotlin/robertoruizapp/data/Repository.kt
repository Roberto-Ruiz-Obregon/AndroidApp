package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.model.*
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile

class Repository() {
    private lateinit var api: ApiService

    suspend fun getCursos(): CursosObjeto?{
        api = NetworkModuleDI()
        return try{
            api.getCursos()
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

   // suspend fun getUserInfo(id:String): UserInfo? = getUser(id)

}