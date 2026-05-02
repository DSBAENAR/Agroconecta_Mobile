package com.agroconecta.mobile.data.remote.dto

data class PurchaseDto(
    val id: Int,
    val productId: Int,
    val farmerId: Int,
    val buyerId: Int,
    val quantity: Float,
    val price: Float,
    val totalPrice: Double,
    val status: String,
    val createdAt: String
)