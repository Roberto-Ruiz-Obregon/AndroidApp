package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.domain.ProgramListRequirement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ProgamViewModel that manages the activity actions
 *
 */
class ProgramViewModel : ViewModel() {
    // [Cajita],
    val finishedLoading = MutableLiveData<Boolean>()
    val programObjectLiveData = MutableLiveData<List<Document>>()
    private val programListRequirement = ProgramListRequirement()

    /**
     * Calls the API service to get the information about the program
     * according to the [ProgramListRequirement] method
     *
     * @param programName name of the [Program]-results-data
     * @param categorySelected category selected by the user
     */
    fun getProgramList(programName: String, categorySelected: String) {
        finishedLoading.postValue(false)
        CoroutineScope(Dispatchers.IO).launch {
            val result: Program? = programListRequirement(programName, categorySelected)
            val programs: List<Document>? = result?.data?.documents

            withContext(Dispatchers.Main.immediate) {
                programObjectLiveData.postValue(programs!!) // !! "Sé lo que estoy haciendo"
                finishedLoading.postValue(true)
            }
        }
    }
}