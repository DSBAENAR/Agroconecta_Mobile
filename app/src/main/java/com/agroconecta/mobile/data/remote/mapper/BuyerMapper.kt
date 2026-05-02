package com.agroconecta.mobile.data.remote.mapper

import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.UserStatus
import com.agroconecta.mobile.data.remote.dto.BuyerDto

fun BuyerDto.toDomain(): Buyer {
    return Buyer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        status = UserStatus.from(status)
    )
}