package com.ps28372.kotlin_asm.model

import com.google.gson.annotations.SerializedName

class User {
    val id: Int = 0

    @SerializedName("full_name")
    val fullName: String = ""
    val email: String = ""
    val password: String = ""
}

class UserResponse {
    val error: String = ""
    val token: String = ""
    val message: String = ""
    val user: User = User()
}