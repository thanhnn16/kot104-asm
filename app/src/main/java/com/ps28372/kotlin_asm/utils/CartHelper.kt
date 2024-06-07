package com.ps28372.kotlin_asm.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ps28372.kotlin_asm.model.Product

data class CartItem(val product: Product, var quantity: Int)

class CartHelper(context: Context) {
    private val items: MutableList<CartItem> = loadCart(context) ?: mutableListOf()

    fun addItem(product: Product, quantity: Int = 1) {
        val existingItem = items.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            items.add(CartItem(product, quantity))
        }
    }

    fun removeItem(product: Product, context: Context) {
        items.removeAll { it.product.id == product.id }
        saveCart(context)
        reloadCart(context)
    }

    fun getTotal(): Double {
        return items.sumOf {
            it.product.price.toDouble() * it.quantity.toDouble()
        }
    }

    fun getItems(): List<CartItem> {
        return items
    }

    fun updateQuantity(product: Product, quantity: Int, context: Context) {
        val existingItem = items.find { it.product.id == product.id }
        existingItem?.quantity = quantity
        saveCart(context)
        reloadCart(context)
    }

    fun saveCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(items)
        editor.putString("cart", json)
        editor.apply()
    }

    private fun loadCart(context: Context): MutableList<CartItem>? {
        val sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cart", null)
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun reloadCart(context: Context) {
        items.clear()
        items.addAll(loadCart(context) ?: mutableListOf())
    }
}
