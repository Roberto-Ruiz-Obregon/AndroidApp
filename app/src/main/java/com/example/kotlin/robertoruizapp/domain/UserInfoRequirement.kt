package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.UserInfo

class UserInfoRequirement {
    private val repository = Repository()
    suspend operator fun invoke(
        id: String
    ): UserInfo? = repository.getUserInfo(id)
}