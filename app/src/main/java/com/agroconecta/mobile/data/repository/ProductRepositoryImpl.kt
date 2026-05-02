package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.mock.MockProducts
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.remote.RetrofitInstance
import com.agroconecta.mobile.data.remote.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl : ProductRepository {

    private val api = RetrofitInstance.productApi

    override suspend fun getAllProducts(): List<Product> =
        withContext(Dispatchers.IO) {
            try {
                api.getProducts().map { it.toDomain() }
            } catch (_: Exception) {
                MockProducts.products
            }
        }

    override suspend fun getProductById(id: Int): Product? =
        withContext(Dispatchers.IO) {
            try {
                api.getProductById(id).toDomain()
            } catch (_: Exception) {
                MockProducts.products.find { it.id == id }
            }
        }

    override suspend fun getProductsByFarmer(farmerId: Int): List<Product> =
        withContext(Dispatchers.IO) {
            try {
                api.getProductsByFarmer(farmerId)
                    .map { it.toDomain() }
            } catch (_: Exception) {
                MockProducts.products.filter {
                    it.farmerId == farmerId
                }
            }
        }

    override suspend fun searchProducts(query: String): List<Product> =
        withContext(Dispatchers.IO) {
            try {
                api.searchProducts(query)
                    .map { it.toDomain() }
            } catch (_: Exception) {
                MockProducts.products.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.category.contains(query, ignoreCase = true) ||
                            it.location.contains(query, ignoreCase = true)
                }
            }
        }
}