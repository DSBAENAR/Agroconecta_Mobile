package com.agroconecta.mobile.data.mock

import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.data.session.UserSession

object MockAuth {

    fun loginBuyer(): UserSession {
        return UserSession(
            userId = 1,
            name = "COMPRADOR MOCK ALEJO 1B",
            email = "buyer@agroconecta.com",
            phone = "+57 300 000 0001",
            role = UserRole.BUYER,
            token = generateMockToken(1, UserRole.BUYER)
        )
    }

    fun loginFarmer(): UserSession {
        return UserSession(
            userId = 2,
            name = "Carlos Gómez",
            email = "farmer@agroconecta.com",
            phone = "+57 300 000 0002",
            role = UserRole.FARMER,
            token = generateMockToken(2, UserRole.FARMER)
        )
    }

    private fun generateMockToken(userId: Int, role: UserRole): String {
        return "mock_token_${userId}_${role.name}_${System.currentTimeMillis()}"
    }
}