package com.ps28372.kotlin_asm.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitInstance {
    companion object {
        private const val BASE_URL = "http://kot_asm_api.test/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}