package com.agroconecta.mobile.data.model

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val unit: Float,
    val available: Float,
    val minOrder: Float,
    val location: String,
    val rating: Float,
    val imageUrl: String = "",
    val description: String = "",
    val farmerId: Int,
    val status: ProductStatus = ProductStatus.ACTIVE
)

enum class ProductStatus {
    ACTIVE,
    SOLD,
    PAUSED
}