package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.session.UserSession
import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer

interface UserRepository {

    suspend fun getFarmerById(id: Int): Farmer?

    suspend fun getBuyerById(id: Int): Buyer?

    suspend fun login(email: String, password: String): UserSession?

    suspend fun updateBuyer(buyer: Buyer): Buyer

    suspend fun updateFarmer(farmer: Farmer): Farmer
}