package com.agroconecta.mobile.data.session

data class UserSession(
    val userId: Int = -1,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: UserRole,
    val token: String = ""
)