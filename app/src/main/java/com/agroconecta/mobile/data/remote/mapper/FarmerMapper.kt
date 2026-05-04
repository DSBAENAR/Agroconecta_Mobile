package com.agroconecta.mobile.data.remote.mapper

import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.model.UserStatus
import com.agroconecta.mobile.data.remote.dto.FarmerDto

fun FarmerDto.toDomain(): Farmer {
    return Farmer(
        id = id,
        name = name,
        location = location,
        email = email,
        phone = phone,
        imageUrl = imageUrl,
        status = UserStatus.from(status)
    )
}