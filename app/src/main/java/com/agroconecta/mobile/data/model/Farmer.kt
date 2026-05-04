package com.agroconecta.mobile.data.model

data class Farmer(
    val id: Int,
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
    val imageUrl: String? = null,
    val status: UserStatus = UserStatus.ACTIVE
)