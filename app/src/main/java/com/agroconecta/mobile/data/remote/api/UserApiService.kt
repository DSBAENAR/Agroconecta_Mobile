package com.agroconecta.mobile.data.remote.api

import com.agroconecta.mobile.data.remote.dto.BuyerDto
import com.agroconecta.mobile.data.remote.dto.FarmerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("buyers/{id}")
    suspend fun getBuyerById(
        @Path("id") id: Int
    ): BuyerDto

    @GET("farmers/{id}")
    suspend fun getFarmerById(
        @Path("id") id: Int
    ): FarmerDto
}