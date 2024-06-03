package com.ps28372.kotlin_asm.repository

import com.ps28372.kotlin_asm.api.ApiService
import com.ps28372.kotlin_asm.api.RetrofitInstance

class ProductRepository {
    private val apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

    suspend fun getProducts() = apiService.getProducts()

    suspend fun getCategories() = apiService.getCategories()

    suspend fun getCategory(id: Int) = apiService.getCategory(id)

    suspend fun getProductsByCategory(category: Int) = apiService.getProductsByCategory(category)

    suspend fun getProduct(id: Int) = apiService.getProduct(id)
}