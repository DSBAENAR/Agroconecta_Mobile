package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import java.time.LocalDateTime

object MockPurchases {

    val purchases = mutableListOf(

        Purchase(
            id = 1,
            productId = 1,
            productName = "Papa Criolla",
            farmerId = 1,
            buyerId = 1,
            quantity = 50.0,
            price = 3500.0,
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
            quantity = 30.0,
            price = 2800.0,
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
            quantity = 75.0,
            price = 4200.0,
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
            quantity = 20.0,
            price = 12500.0,
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
            quantity = 40.0,
            price = 7800.0,
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
            quantity = 60.0,
            price = 3400.0,
            totalPrice = 204000.0,
            status = PurchaseStatus.IN_TRANSIT,
            createdAt = LocalDateTime.of(2026, 4, 30, 13, 5)
        )
    )
}