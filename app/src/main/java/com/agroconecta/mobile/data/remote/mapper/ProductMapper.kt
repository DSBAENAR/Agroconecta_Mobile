package com.agroconecta.mobile.data.remote.mapper

import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.model.ProductStatus
import com.agroconecta.mobile.data.remote.dto.ProductDto

fun ProductDto.toDomain(): Product {

    return Product(
        id = id,
        name = name,
        category = category,
        price = price,
        unit = unit,
        available = available,
        minOrder = minOrder,
        location = location,
        rating = rating,
        imageUrl = imageUrl.orEmpty(),
        description = description.orEmpty(),
        farmerId = farmerId,
        status = ProductStatus.from(status)
    )
}

fun Product.toDto(): ProductDto {

    return ProductDto(
        id = id,
        name = name,
        category = category,
        price = price,
        unit = unit,
        available = available,
        minOrder = minOrder,
        location = location,
        rating = rating,
        imageUrl = imageUrl,
        description = description,
        farmerId = farmerId,
        status = status.name
    )
}