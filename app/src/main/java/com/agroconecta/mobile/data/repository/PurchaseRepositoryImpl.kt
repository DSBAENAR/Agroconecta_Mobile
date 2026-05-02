package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.mock.MockPurchases
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.remote.RetrofitInstance
import com.agroconecta.mobile.data.remote.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PurchaseRepositoryImpl : PurchaseRepository {

    private val api = RetrofitInstance.purchaseApi

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
                api.getPurchasesByBuyer(buyerId)
                    .map { it.toDomain() }
            } catch (_: Exception) {
                MockPurchases.purchases.filter {
                    it.buyerId == buyerId
                }
            }
        }
}