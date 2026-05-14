    package com.agroconecta.mobile.data.model
    
    data class Product(
        val id: Int,
        val name: String,
        val category: String,
        val price: Double,
        val unit: String,
        val available: Double,
        val minOrder: Double,
        val location: String,
        val rating: Float,
        val imageUrl: String = "",
        val description: String = "",
        val farmerId: Int,
        val status: ProductStatus = ProductStatus.ACTIVE
    )
    
