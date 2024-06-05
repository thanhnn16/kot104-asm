package com.ps28372.kotlin_asm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    val product = MutableLiveData<Product>()

    private val productRepository = ProductRepository()

    fun getProduct(id: Int) {
        viewModelScope.launch {
            val productData = productRepository.getProduct(id)
            product.value = productData
        }
    }
}