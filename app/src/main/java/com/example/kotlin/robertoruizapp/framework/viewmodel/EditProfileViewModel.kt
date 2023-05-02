package com.example.kotlin.robertoruizapp.framework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileResponse
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * EditProfileViewModel that manages the activity actions
 *
 */
class EditProfileViewModel : ViewModel() {
    var editUserLiveData: MutableLiveData<EditProfileResponse?> = MutableLiveData()

    /**
     * Gets the editUserLiveData variable
     *
     * @return MutableLiveData Object
     */
    fun getProfileUserObserver(): MutableLiveData<EditProfileResponse?> {
        return editUserLiveData
    }

    /**
     * Calls the editMyInfo method from [Repository]
     * and returns the information of user to edit
     *
     * @param user [EditProfileRequest] object
     */
    fun editMyInfo(user: EditProfileRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: EditProfileResponse? =
                repository.editMyInfo("Bearer ${LoginActivity.token}", user)

            CoroutineScope(Dispatchers.Main).launch {
                editUserLiveData.postValue(result)
            }
        }
    }

}

