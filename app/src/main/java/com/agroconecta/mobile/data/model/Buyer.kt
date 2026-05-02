package com.agroconecta.mobile.data.model

data class Buyer(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val status: UserStatus = UserStatus.ACTIVE
)