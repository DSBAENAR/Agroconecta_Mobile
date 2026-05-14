package com.agroconecta.mobile.data.remote.dto

data class PurchaseDto(
    val id: Int,
    val productId: Int,
    val productName: String,
    val farmerId: Int,
    val buyerId: Int,
    val quantity: Double,
    val price: Double,
    val totalPrice: Double,
    val status: String,
    val createdAt: String
)