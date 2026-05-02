package com.agroconecta.mobile.data.remote.mapper

import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.data.remote.dto.PurchaseDto
import java.time.LocalDateTime

fun PurchaseDto.toDomain(): Purchase {
    return Purchase(
        id = id,
        productId = productId,
        farmerId = farmerId,
        buyerId = buyerId,
        quantity = quantity,
        price = price,
        totalPrice = totalPrice,
        status = PurchaseStatus.from(status),
        createdAt = LocalDateTime.parse(createdAt)
    )
}