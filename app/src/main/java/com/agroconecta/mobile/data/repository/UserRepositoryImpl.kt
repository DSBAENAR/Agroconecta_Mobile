package com.agroconecta.mobile.data.repository

import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.remote.api.UserApiService
import com.agroconecta.mobile.data.remote.mapper.toDomain
import com.agroconecta.mobile.data.mock.MockFarmers
import com.agroconecta.mobile.data.mock.MockAuth
import com.agroconecta.mobile.data.mock.MockBuyers
import com.agroconecta.mobile.data.session.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApiService
) : UserRepository {

    override suspend fun getFarmerById(id: Int): Farmer? =
        withContext(Dispatchers.IO) {
            try {
                api.getFarmerById(id).toDomain()
            } catch (e: Exception) {
                MockFarmers.farmers.find { it.id == id }
            }
        }

    override suspend fun getBuyerById(id: Int): Buyer? =
        withContext(Dispatchers.IO) {
            try {
                api.getBuyerById(id).toDomain()
            } catch (e: Exception) {
                MockBuyers.buyers.find { it.id == id }
            }
        }

    override suspend fun login(
        email: String,
        password: String
    ): UserSession? =
        withContext(Dispatchers.IO) {

            val session: UserSession? = when {
                email.contains("farmer", ignoreCase = true) ->
                    MockAuth.loginFarmer()

                email.contains("buyer", ignoreCase = true) ->
                    MockAuth.loginBuyer()

                else -> MockAuth.loginBuyer()
            }

            session?.let {
                com.agroconecta.mobile.data.session.SessionManager.saveSession(it)
            }

            session
        }
}