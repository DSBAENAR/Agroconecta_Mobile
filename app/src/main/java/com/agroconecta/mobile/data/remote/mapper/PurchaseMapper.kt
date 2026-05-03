package com.agroconecta.mobile.data.remote.mapper

import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.data.remote.dto.PurchaseDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun PurchaseDto.toDomain(): Purchase {
    val parsedDate = tryParseDate(createdAt)

    return Purchase(
        id = id,
        productId = productId,
        farmerId = farmerId,
        buyerId = buyerId,
        quantity = quantity,
        price = price,
        totalPrice = totalPrice,
        status = PurchaseStatus.from(status),
        createdAt = parsedDate
    )
}

private fun tryParseDate(raw: String): LocalDateTime {
    val formatters = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,           // 2024-01-15T10:30:00
        DateTimeFormatter.ISO_DATE_TIME,                 // 2024-01-15T10:30:00Z
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // 2024-01-15 10:30:00
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"), // con milisegundos
    )

    for (formatter in formatters) {
        try {
            return LocalDateTime.parse(raw, formatter)
        } catch (_: DateTimeParseException) {
            continue
        }
    }

    // Último recurso: fecha actual para no crashear
    return LocalDateTime.now()
}