package com.ps28372.kotlin_asm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.model.ProductCategory
import com.ps28372.kotlin_asm.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(token: String = "") : ViewModel() {
    val isLoading = MutableLiveData(true)
    val productCategories = MutableLiveData<List<ProductCategory>>(emptyList())
    val products = MutableLiveData<List<Product>>(emptyList())

    private val productRepository = ProductRepository(token)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val loadedProductCategories = productRepository.getCategories()
                val loadedProducts = productRepository.getProducts()
                productCategories.value = loadedProductCategories
                products.value = loadedProducts
                isLoading.value = false
                Log.d("HomeVM", "loadData: Success")
            } catch (e: Exception) {
                isLoading.value = false
                e.printStackTrace()
            }
        }
    }

    fun getProductsByCategory(categoryId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val loadedProducts = productRepository.getProductsByCategory(categoryId)
                products.value = loadedProducts
                isLoading.value = false
                Log.d("HomeVM", "getProductsByCategory: Success")
            } catch (e: Exception) {
                isLoading.value = false
                e.printStackTrace()
            }
        }
    }
}