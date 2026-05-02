package com.agroconecta.mobile.data.remote.dto

data class FarmerDto(
    val id: Int,
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
    val status: String
)