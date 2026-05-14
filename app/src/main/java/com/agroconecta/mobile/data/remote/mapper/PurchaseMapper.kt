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
        productName = productName,
        farmerId = farmerId,
        buyerId = buyerId,
        quantity = quantity,
        price = price,
        totalPrice = totalPrice,
        status = PurchaseStatus.from(status),
        createdAt = parsedDate
    )
}

fun Purchase.toDto(): PurchaseDto {

    return PurchaseDto(
        id = id,
        productId = productId,
        productName = productName,
        farmerId = farmerId,
        buyerId = buyerId,
        quantity = quantity,
        price = price,
        totalPrice = totalPrice,
        status = status.name,
        createdAt = createdAt.format(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
        )
    )
}

private fun tryParseDate(raw: String): LocalDateTime {

    val formatters = listOf(

        DateTimeFormatter.ISO_LOCAL_DATE_TIME,

        DateTimeFormatter.ISO_DATE_TIME,

        DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss"
        ),

        DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss.SSS"
        )
    )

    for (formatter in formatters) {

        try {

            return LocalDateTime.parse(
                raw,
                formatter
            )

        } catch (_: DateTimeParseException) {
            continue
        }
    }

    return LocalDateTime.now()
}