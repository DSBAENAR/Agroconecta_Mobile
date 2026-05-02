package com.agroconecta.mobile.data.model

enum class PurchaseStatus {
    DELIVERED,
    IN_TRANSIT,
    PENDING,
    CANCELLED,
    UNKNOWN;

    companion object {
        fun from(value: String): PurchaseStatus {
            return entries.firstOrNull {
                it.name.equals(value, ignoreCase = true)
            } ?: UNKNOWN
        }
    }
}