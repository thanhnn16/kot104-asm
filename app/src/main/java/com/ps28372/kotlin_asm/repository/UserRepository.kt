package com.ps28372.kotlin_asm.repository

import com.ps28372.kotlin_asm.api.ApiService
import com.ps28372.kotlin_asm.api.LoginRequest
import com.ps28372.kotlin_asm.api.RegisterRequest
import com.ps28372.kotlin_asm.api.RetrofitInstance
import com.ps28372.kotlin_asm.model.UserResponse

class UserRepository {
    private val apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

    suspend fun login(email: String, password: String): UserResponse {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    suspend fun register(fullName: String, email: String, password: String): UserResponse {
        val request = RegisterRequest(fullName, email, password)
        return apiService.register(request)
    }
}