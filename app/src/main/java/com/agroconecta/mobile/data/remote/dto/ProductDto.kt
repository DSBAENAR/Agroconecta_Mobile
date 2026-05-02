package com.agroconecta.mobile.data.remote.dto

data class ProductDto(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val unit: String,
    val available: Float,
    val minOrder: Float,
    val location: String,
    val rating: Float,
    val imageUrl: String?,
    val description: String?,
    val farmerId: Int,
    val status: String
)