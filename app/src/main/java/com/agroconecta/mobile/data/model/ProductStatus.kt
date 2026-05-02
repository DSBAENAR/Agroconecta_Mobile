package com.agroconecta.mobile.data.model

enum class ProductStatus {
    ACTIVE,
    SOLD,
    PAUSED,
    UNKNOWN;

    companion object {
        fun from(value: String): ProductStatus {
            return entries.firstOrNull {
                it.name.equals(value, ignoreCase = true)
            } ?: UNKNOWN
        }
    }
}