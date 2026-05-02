package com.agroconecta.mobile.data.remote.api

import com.agroconecta.mobile.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDto

    @GET("products")
    suspend fun getProductsByFarmer(
        @Query("farmerId") farmerId: Int
    ): List<ProductDto>

    @GET("products/search")
    suspend fun searchProducts(
        @Query("query") query: String
    ): List<ProductDto>
}