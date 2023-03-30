package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.domain.ProgramListRequirement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel() {
    // [Cajita],
    val programObjectLiveData = MutableLiveData<List<Document>>()
    private val programListRequirement = ProgramListRequirement()



    fun getProgramList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Program? = programListRequirement()
            var programs: List<Document>? = result?.data?.documents
            Log.d("Salida",programs.toString())
            CoroutineScope(Dispatchers.Main).launch{
                programObjectLiveData.postValue(programs!!) // !! "Sé lo que estoy haciendo"
            }
        }
    }
}