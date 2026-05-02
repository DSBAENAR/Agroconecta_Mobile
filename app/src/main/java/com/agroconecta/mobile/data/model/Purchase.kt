package com.agroconecta.mobile.data.model

data class Purchase(
    val id: Int,
    val productId: Int,
    val farmerId: Int,
    var buyerId: Int,
    val quantity: Float,
    val price: Float,
    val totalPrice: Double,
    val status: PurchaseStatus
)

enum class PurchaseStatus {
    DELIVERED,
    IN_TRANSIT,
    PENDING,
    CANCELLED
}