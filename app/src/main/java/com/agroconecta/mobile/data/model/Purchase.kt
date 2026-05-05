package com.agroconecta.mobile.data.model

import java.time.LocalDateTime

data class Purchase(
    val id: Int,
    val productId: Int,
    val productName: String,
    val farmerId: Int,
    val buyerId: Int,
    val quantity: Float,
    val price: Float,
    val totalPrice: Double,
    val status: PurchaseStatus,
    val createdAt: LocalDateTime
)

