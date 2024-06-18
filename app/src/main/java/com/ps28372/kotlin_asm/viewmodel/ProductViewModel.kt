package com.ps28372.kotlin_asm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(token: String) : ViewModel() {
    val product = MutableLiveData<Product>()
    val favoriteProducts = MutableLiveData<List<Product>>()
    val isLoading = MutableLiveData(false)

    val searchProducts = MutableLiveData<List<Product>>()

    private val productRepository = ProductRepository(token)

    fun getProduct(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val productData = productRepository.getProduct(id)
            product.value = productData
            isLoading.value = false
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch {
            isLoading.value = true
            val favoriteProductsData = productRepository.getFavoriteProducts()
            favoriteProducts.value = favoriteProductsData
            isLoading.value = false
        }
    }

    fun getSearchProducts(name: String) {
        viewModelScope.launch {
            isLoading.value = true
            val searchProductsData = productRepository.getSearchProducts(name)
            searchProducts.value = searchProductsData
            isLoading.value = false
        }
    }

    fun addFavoriteProduct(id: Int) {
        viewModelScope.launch {
            productRepository.addFavoriteProduct(id)
            getFavoriteProducts()
        }
    }

    fun removeFavoriteProduct(id: Int) {
        viewModelScope.launch {
            productRepository.removeFavoriteProduct(id)
            getFavoriteProducts()
        }
    }
}