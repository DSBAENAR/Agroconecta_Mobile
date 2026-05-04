package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.remote.api.UserApiService
import com.agroconecta.mobile.data.remote.mapper.toDomain
import com.agroconecta.mobile.data.mock.MockFarmers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApiService
) : UserRepository {

    override suspend fun getFarmerById(id: Int): Farmer? =
        withContext(Dispatchers.IO) {
            try {
                api.getFarmerById(id).toDomain()
            } catch (_: Exception) {
                MockFarmers.farmers.find { it.id == id }
            }
        }

    override suspend fun getBuyerById(id: Int): Buyer? =
        withContext(Dispatchers.IO) {
            try {
                api.getBuyerById(id).toDomain()
            } catch (_: Exception) {
                null
            }
        }

    override suspend fun login(
        email: String,
        password: String
    ): Any? = withContext(Dispatchers.IO) {
        Any()
    }
}