package com.example.kotlin.robertoruizapp.data.network

import com.example.kotlin.robertoruizapp.data.network.model.program.Program

/**
 * ProgramApiClient that is used as a controller to handle network between App and Database
 * instance.
 *
 */
class ProgramApiClient {
    private lateinit var api: ProgramAPIService

    /**
     * Gets the list of [Program] objects that match the category given
     *
     * @param programName "" as defualt name to match any
     * @param categorySelected category of the programs
     *
     * @return list of [Program] objects
     */
    suspend fun getProgramList(programName: String, categorySelected: String): Program? {
        api = NetworkModuleDI()
        return try {
            api.getProgramList(programName, categorySelected)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the information of [Program] given the id of the program
     *
     * @param idProgram id of the program
     *
     * @return [Program] object
     */
    suspend fun getProgramInfo(idProgram: String): Program? {
        api = NetworkModuleDI()
        return try {
            api.getProgramInfo(idProgram)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

}