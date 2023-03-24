package com.example.kotlin.robertoruizapp

enum class Gender(val value: String){
    HOMBRE("Hombre"),
    MUJER("Mujer"),
    PREFIERO_NO_DECIR("Prefiero no decir")
}

data class User(val id: String?, val name: String?, val age: Int?, val sex: String?, val education: String?,
                val job: String?, val postalCode: Int?, val email: String?, val password: String?, val cnfPassword: String?)
data class UserResponse(val status: String?, val message: String?)

