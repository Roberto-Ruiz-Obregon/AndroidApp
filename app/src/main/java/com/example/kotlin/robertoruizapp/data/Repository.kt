package com.example.kotlin.robertoruizapp.data

import com.example.kotlin.robertoruizapp.data.network.model.*
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileResponse
import com.example.kotlin.robertoruizapp.data.network.model.Topic.TopicsObject
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile

/**
 * Repository class that has the methods to call the [NetworkModuleDI]
 *
 */
class Repository() {
    private lateinit var api: ApiService

    /**
     * Gets the [CursosObjeto] calling the [NetworkModuleDI]
     *
     * @return the call to the [NetworkModuleDI] method [getCursosNoFilter]
     */
    suspend fun getCursosNoFilter(jwt:String) : CursosObjeto? {
        api = NetworkModuleDI()
        return try{
            api.getCursosNoFilter(jwt)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * gets the [CursosObjeto] calling the [NetworkModuleDI]
     *
     * @param courseName the name of the course
     * @param postalCode postal code of course
     * @param modality how the course is presented
     * @param status status of the course
     * @param topic the about of the course
     *
     * @return [CursosObjeto] object
     */
    suspend fun getCursos(jwt:String, courseName: String, postalCode: String, modality: String, status: String, topic: String?): CursosObjeto?{
        api = NetworkModuleDI()
        return try{
            api.getCursos(jwt,courseName, postalCode, modality, status, topic)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getCursosRecomendados(postalCode: String): CursosObjeto? {
        api = NetworkModuleDI()
        return try {
            api.getCursosRecomendados(postalCode)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the [TopicsObject] from [NetworkModuleDI]
     *
     * @return [TopicsObject] object
     */
    suspend fun getTopics(jwt:String): TopicsObject?{
        api = NetworkModuleDI()
        return try{
            api.getTopics(jwt)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the user profile usind their [id]
     * @param [id] user idenifier
     * @return the API response
     *
     */
    suspend fun getUserInfo(id:String): Profile?{
        api = NetworkModuleDI()
        return try{
            api.getUserInfo(id)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the user [Profile] using their [jwt]
     * @param [jwt] Authorization header of the user
     * @return the API response in [Profile]
     */
    suspend fun getMyInfo(jwt:String): Profile?{
        api = NetworkModuleDI()
        return try{
            api.getMyInfo(jwt)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the courses [CursosObjeto] using their [jwt]
     * @param [jwt] Authorization header of the user
     * @return the API response in [CursosObjeto]
     */
    suspend fun getMyCourses(jwt:String): CursosObjeto?{
        api = NetworkModuleDI()
        return try{
            api.getMyCourses(jwt)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Edits the user [Profile]
     * @param [jwt] Authorization header of the user
     * @param [request] Changed fields for the user's profile using a [EditProfileRequest]
     * @return the API response [EditProfileResponse]
     */
    suspend fun editMyInfo(jwt: String, request: EditProfileRequest): EditProfileResponse? {
        api = NetworkModuleDI()
        return try{
            api.editMyInfo(jwt, request)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            null
        }
    }
}