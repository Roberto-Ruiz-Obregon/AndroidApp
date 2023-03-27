package com.example.kotlin.robertoruizapp.framework.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.robertoruizapp.data.network.model.ProgramObject
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.domain.ProgramListRequirement
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel() {

    val programObjectLiveData = MutableLiveData<ProgramObject>()
    private val programListRequirement = ProgramListRequirement()



    fun getProgramList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Program? = programListRequirement(Constants.MAX_PROGRAM_NUMBER)
            Log.d("Salida",result?.status.toString())
            /*CoroutineScope(Dispatchers.Main).launch{
                programObjectLiveData.postValue(result!!)
            }*/
        }
    }
}