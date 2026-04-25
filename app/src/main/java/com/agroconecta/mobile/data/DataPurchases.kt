package com.agroconecta.mobile.data

import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus

object DataPurchases {

    private val purchases = listOf(
        Purchase(
            id = "1",
            productName = "Tomates Cherry Premium",
            sellerName = "Juan Pérez",
            quantity = 50,
            unit = "kg",
            totalPrice = 175000.0,
            status = PurchaseStatus.DELIVERED
        ),
        Purchase(
            id = "2",
            productName = "Lechuga Orgánica",
            sellerName = "María González",
            quantity = 30,
            unit = "kg",
            totalPrice = 84000.0,
            status = PurchaseStatus.IN_TRANSIT
        ),
        Purchase(
            id = "3",
            productName = "Papas Criollas",
            sellerName = "Ana Rodríguez",
            quantity = 100,
            unit = "kg",
            totalPrice = 320000.0,
            status = PurchaseStatus.PENDING
        ),
        Purchase(
            id = "4",
            productName = "Mango Tommy Exportación",
            sellerName = "Carlos Ramírez",
            quantity = 75,
            unit = "kg",
            totalPrice = 315000.0,
            status = PurchaseStatus.CANCELLED
        )
    )

    fun getAllPurchases(): List<Purchase> = purchases

    fun getRecentPurchases(limit: Int = 10): List<Purchase> {
        return purchases.take(limit)
    }

    fun getPendingPurchases(): List<Purchase> {
        return purchases.filter {
            it.status == PurchaseStatus.PENDING
        }
    }

    fun getTotalSpent(): Double {
        return purchases.sumOf {
            it.totalPrice
        }
    }

    fun getTotalPurchases(): Int {
        return purchases.size
    }

    fun getPurchaseById(id: String): Purchase? {
        return purchases.find {
            it.id == id
        }
    }
}