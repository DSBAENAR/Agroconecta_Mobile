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
        password: String,
        isFarmer: Boolean
    ): UserSession? =
        withContext(Dispatchers.IO) {

            val session: UserSession? = when {
                isFarmer || email.contains("farmer", ignoreCase = true) ->
                    MockAuth.loginFarmer()

                else -> MockAuth.loginBuyer()
            }

            session?.let {
                com.agroconecta.mobile.data.session.SessionManager.saveSession(it)
            }

            session
        }
    override suspend fun updateBuyer(
        buyer: Buyer
    ): Buyer =
        withContext(Dispatchers.IO) {

            try {
                // futuro backend
                buyer

            } catch (e: Exception) {

                val index = MockBuyers.buyers.indexOfFirst {
                    it.id == buyer.id
                }

                if (index != -1) {
                    MockBuyers.buyers[index] = buyer
                }

                buyer
            }
        }

    override suspend fun updateFarmer(
        farmer: Farmer
    ): Farmer =
        withContext(Dispatchers.IO) {

            try {
                // futuro backend
                farmer

            } catch (e: Exception) {

                val index = MockFarmers.farmers.indexOfFirst {
                    it.id == farmer.id
                }

                if (index != -1) {
                    MockFarmers.farmers[index] = farmer
                }

                farmer
            }
        }
}