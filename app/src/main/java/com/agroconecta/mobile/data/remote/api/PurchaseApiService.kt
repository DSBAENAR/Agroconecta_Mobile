package com.agroconecta.mobile.data.remote.api

import com.agroconecta.mobile.data.remote.dto.PurchaseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PurchaseApiService {

    @GET("purchases")
    suspend fun getPurchases(): List<PurchaseDto>

    @GET("purchases/{id}")
    suspend fun getPurchaseById(
        @Path("id") id: Int
    ): PurchaseDto?

    @GET("purchases")
    suspend fun getPurchasesByBuyer(
        @Query("buyerId") buyerId: Int
    ): List<PurchaseDto>
}