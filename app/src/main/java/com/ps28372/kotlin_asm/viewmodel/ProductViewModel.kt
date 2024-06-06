package com.ps28372.kotlin_asm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    val product = MutableLiveData<Product>()

    val isLoading = MutableLiveData(true)

    private val productRepository = ProductRepository()

    fun getProduct(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val productData = productRepository.getProduct(id)
            product.value = productData
            isLoading.value = false
        }
    }
}