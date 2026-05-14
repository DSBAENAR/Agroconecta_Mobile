package com.agroconecta.mobile.data.model

data class CartItem(
    val product: Product,
    val quantity: Double,
    val totalPrice: Double =
        quantity * product.price
)