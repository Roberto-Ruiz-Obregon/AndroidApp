package com.example.kotlin.robertoruizapp.domain

import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Profile.Profile

/**
 * UserInfoRequirement class which has opertator invoke method
 *
 */
class UserInfoRequirement {
    private val repository = Repository()

    /**
     * Sets the function of the operator invoke as an asynchronous function
     * that calls the getUserInfo method from [Repository]
     *
     * @param id the id of the user
     *
     * @return Profile Type Object
     */
    suspend operator fun invoke(
        id: String
    ): Profile? = repository.getUserInfo(id)
}