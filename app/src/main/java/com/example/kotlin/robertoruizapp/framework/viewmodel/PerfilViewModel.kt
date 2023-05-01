package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity.UserToken.token
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * PerfilViewModel that manages the activity actions
 * This class persists the information data of a user
 *
 */
class PerfilViewModel : ViewModel() {
    val userLiveData = MutableLiveData<Profile?>()

    /**
     * Calls the [Profile] information from the API service
     * posting the resul on userLiveData
     *
     */
    fun getUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.getUserInfo(Constants.ID_PERFIL)

            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }

    /**
     * Calls the [Profile] information from the API service.
     * The information requested is based of the session token of the user
     * as a jwt
     *
     */
    fun getMyInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: Profile? = repository.getMyInfo("jwt=${token}")

            CoroutineScope(Dispatchers.Main).launch {
                userLiveData.postValue(result)
            }
        }
    }

}