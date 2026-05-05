package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.mock.MockPurchases
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.remote.api.PurchaseApiService
import com.agroconecta.mobile.data.remote.mapper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PurchaseRepositoryImpl(
    private val api: PurchaseApiService
) : PurchaseRepository {

    override suspend fun getAllPurchases(): List<Purchase> =
        withContext(Dispatchers.IO) {
            try {
                api.getPurchases().map { it.toDomain() }
            } catch (_: Exception) {
                MockPurchases.purchases
            }
        }

    override suspend fun getPurchaseById(id: Int): Purchase? =
        withContext(Dispatchers.IO) {
            try {
                api.getPurchaseById(id)?.toDomain()
            } catch (_: Exception) {
                MockPurchases.purchases.find { it.id == id }
            }
        }

    override suspend fun getRecentPurchases(limit: Int): List<Purchase> =
        withContext(Dispatchers.IO) {
            try {
                api.getPurchases()
                    .map { it.toDomain() }
                    .sortedByDescending { it.createdAt }
                    .take(limit)
            } catch (_: Exception) {
                MockPurchases.purchases
                    .sortedByDescending { it.createdAt }
                    .take(limit)
            }
        }

    override suspend fun getPurchasesByBuyer(buyerId: Int): List<Purchase> =
        withContext(Dispatchers.IO) {
            try {
                api.getPurchasesByBuyer(buyerId).map { it.toDomain() }
            } catch (_: Exception) {
                MockPurchases.purchases.filter { it.buyerId == buyerId }
            }
        }

    override suspend fun getPurchasesByFarmer(farmerId: Int): List<Purchase> =
        withContext(Dispatchers.IO) {
            try {
                api.getPurchasesByFarmer(farmerId).map { it.toDomain() }
            } catch (_: Exception) {
                MockPurchases.purchases.filter { it.farmerId == farmerId }
            }
        }

    override suspend fun createPurchases(purchases: List<Purchase>): List<Purchase> =
        withContext(Dispatchers.IO) {
            try {
                api.createPurchases(purchases.map { it.toDto() })
                    .map { it.toDomain() }
            } catch (_: Exception) {
                MockPurchases.purchases.addAll(purchases)
                purchases
            }
        }

}