package com.ps28372.kotlin_asm.api

import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.model.ProductCategory
import com.ps28372.kotlin_asm.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/search/{name}")
    suspend fun getSearchProducts(@Path("name") name: String): List<Product>

    @GET("categories")
    suspend fun getCategories(): List<ProductCategory>

    @GET("categories/{id}")
    suspend fun getCategory(@Path("id") id: Int): ProductCategory

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: Int): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("user/favorites")
    suspend fun getFavoriteProducts(): List<Product>

    @POST("user/favorites/{id}")
    suspend fun addFavoriteProduct(@Path("id") id: Int)

    @DELETE("user/favorites/{id}")
    suspend fun removeFavoriteProduct(@Path("id") id: Int)

}
