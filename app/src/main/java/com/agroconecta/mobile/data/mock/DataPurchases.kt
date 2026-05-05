package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import java.time.LocalDateTime

object MockPurchases {

    val purchases = listOf(
        Purchase(
            id = 1,
            productId = 1,
            productName = "Papa Criolla",
            farmerId = 1,
            buyerId = 1,
            quantity = 50f,
            price = 3500f,
            totalPrice = 175000.0,
            status = PurchaseStatus.DELIVERED,
            createdAt = LocalDateTime.of(2026, 4, 10, 9, 30)
        ),
        Purchase(
            id = 2,
            productId = 2,
            productName = "Tomate Chonto",
            farmerId = 1,
            buyerId = 1,
            quantity = 30f,
            price = 2800f,
            totalPrice = 84000.0,
            status = PurchaseStatus.IN_TRANSIT,
            createdAt = LocalDateTime.of(2026, 4, 18, 14, 15)
        ),
        Purchase(
            id = 3,
            productId = 5,
            productName = "Zanahoria Orgánica",
            farmerId = 2,
            buyerId = 2,
            quantity = 75f,
            price = 4200f,
            totalPrice = 315000.0,
            status = PurchaseStatus.PENDING,
            createdAt = LocalDateTime.of(2026, 4, 25, 11, 45)
        ),
        Purchase(
            id = 4,
            productId = 9,
            productName = "Aguacate Hass",
            farmerId = 3,
            buyerId = 2,
            quantity = 20f,
            price = 12500f,
            totalPrice = 250000.0,
            status = PurchaseStatus.CANCELLED,
            createdAt = LocalDateTime.of(2026, 4, 27, 16, 20)
        ),
        Purchase(
            id = 5,
            productId = 12,
            productName = "Café Premium",
            farmerId = 3,
            buyerId = 3,
            quantity = 40f,
            price = 7800f,
            totalPrice = 312000.0,
            status = PurchaseStatus.DELIVERED,
            createdAt = LocalDateTime.of(2026, 4, 29, 8, 10)
        ),
        Purchase(
            id = 6,
            productId = 6,
            productName = "Cebolla Larga",
            farmerId = 2,
            buyerId = 3,
            quantity = 60f,
            price = 3400f,
            totalPrice = 204000.0,
            status = PurchaseStatus.IN_TRANSIT,
            createdAt = LocalDateTime.of(2026, 4, 30, 13, 5)
        )
    )
}