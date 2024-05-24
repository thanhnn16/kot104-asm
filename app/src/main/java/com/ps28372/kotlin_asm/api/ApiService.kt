package com.ps28372.kotlin_asm.api

import com.ps28372.kotlin_asm.model.User
import com.ps28372.kotlin_asm.model.UserResponse
import com.ps28372.kotlin_asm.repository.UserRepository
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): UserResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): UserResponse
}
