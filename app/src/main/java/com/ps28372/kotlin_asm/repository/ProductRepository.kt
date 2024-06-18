package com.ps28372.kotlin_asm.repository

import com.ps28372.kotlin_asm.api.ApiService
import com.ps28372.kotlin_asm.api.RetrofitInstance
import com.ps28372.kotlin_asm.model.Product

class ProductRepository(token: String) {
    private val apiService = RetrofitInstance.getRetrofitInstance(token).create(ApiService::class.java)

    suspend fun getProducts() = apiService.getProducts()

    suspend fun getCategories() = apiService.getCategories()

    suspend fun getCategory(id: Int) = apiService.getCategory(id)

    suspend fun getProductsByCategory(category: Int) = apiService.getProductsByCategory(category)

    suspend fun getProduct(id: Int): Product {
        try {
            val response = apiService.getProduct(id)
            return response
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    suspend fun getFavoriteProducts(): List<Product> {
        try {
            val response = apiService.getFavoriteProducts()
            return response
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
    
    suspend fun getSearchProducts(name: String): List<Product> {
        return try {
            apiService.getSearchProducts(name)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    suspend fun addFavoriteProduct(id: Int) {
        try {
            apiService.addFavoriteProduct(id)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    suspend fun removeFavoriteProduct(id: Int) {
        try {
            apiService.removeFavoriteProduct(id)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}