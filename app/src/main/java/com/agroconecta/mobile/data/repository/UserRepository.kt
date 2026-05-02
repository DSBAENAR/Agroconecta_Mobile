package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer

interface UserRepository {

    suspend fun getFarmerById(id: Int): Farmer?

    suspend fun getBuyerById(id: Int): Buyer?

    suspend fun login(email: String, password: String): Any?
}