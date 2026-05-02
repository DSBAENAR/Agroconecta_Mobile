package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.model.Product

interface ProductRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getProductById(id: Int): Product?

    suspend fun getProductsByFarmer(farmerId: Int): List<Product>

    suspend fun searchProducts(query: String): List<Product>
}