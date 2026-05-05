package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.model.Purchase

interface PurchaseRepository {

    suspend fun getAllPurchases(): List<Purchase>

    suspend fun getPurchaseById(id: Int): Purchase?

    suspend fun getRecentPurchases(limit: Int = 10): List<Purchase>

    suspend fun getPurchasesByBuyer(buyerId: Int): List<Purchase>

    suspend fun getPurchasesByFarmer(farmerId: Int): List<Purchase>
}