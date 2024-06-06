package com.ps28372.kotlin_asm.model

import com.google.gson.annotations.SerializedName
import com.ps28372.kotlin_asm.utils.BASE_URL

data class Image(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("image_url")
    val imageUrl: String
)

data class Review(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    val rating: Int,
    val comment: String
)

data class ProductCategory(
    val id: Int,
    val name: String,
    val icon: String
)

data class Product(
    var id: Int,
    @SerializedName("category_id")
    private var categoryId: Int,
    val name: String,
    val description: String,
    val price: String,
    val isFavorite: Boolean = false,
    val images: List<Image>,
    val reviews: List<Review>
) {
    fun getRating(): Float {
        var totalRating = 0
        for (review in reviews) {
            totalRating += review.rating
        }
        return totalRating.toFloat() / reviews.size
    }

    fun getReviewCount(): Int {
        return reviews.size
    }

    fun getFirstImageUrl(): String {
        return BASE_URL + images[0].imageUrl
    }

}

// Products response
data class ProductsResponse(
    val error: String,
    val products: List<Product>
)

data class ProductCategoryResponse(
    val error: String,
    val categories: List<ProductCategory>
)