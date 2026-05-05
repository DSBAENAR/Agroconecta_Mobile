package com.agroconecta.mobile.data.session

enum class UserRole {
    BUYER,
    FARMER,
    ADMIN,
    UNKNOWN;

    companion object {
        fun from(value: String): UserRole {
            return entries.firstOrNull {
                it.name.equals(value, ignoreCase = true)
            } ?: UNKNOWN
        }
    }
}