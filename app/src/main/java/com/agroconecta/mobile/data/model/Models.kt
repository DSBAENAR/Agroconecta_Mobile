package com.agroconecta.mobile.data.model

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val unit: String,
    val available: Int,
    val minOrder: Int,
    val location: String,
    val rating: Float,
    val imageUrl: String = "",
    val description: String = "",
    val farmerId: String,
    val status: ProductStatus = ProductStatus.ACTIVE
)

enum class ProductStatus {
    ACTIVE,
    SOLD,
    PAUSED
}

data class Farmer(
    val id: String,
    val name: String,
    val initials: String,
    val isVerified: Boolean,
    val totalSales: Int,
    val totalProducts: Int,
    val rating: Float,
    val clients: Int,
    val location: String,
    val email: String,
    val phone: String,
    val certifications: List<String> = emptyList()
)

data class Purchase(
    val id: String,
    val productName: String,
    val sellerName: String,
    val quantity: Int,
    val unit: String,
    val totalPrice: Double,
    val status: PurchaseStatus
)

enum class PurchaseStatus {
    DELIVERED,
    IN_TRANSIT,
    PENDING,
    CANCELLED
}

data class AIInsight(
    val title: String,
    val description: String,
    val type: InsightType
)

enum class InsightType {
    PRICE,
    HARVEST,
    SHIPPING,
    DEMAND
}

data class DemandItem(
    val product: String,
    val level: DemandLevel
)

enum class DemandLevel {
    HIGH,
    MEDIUM,
    LOW
}