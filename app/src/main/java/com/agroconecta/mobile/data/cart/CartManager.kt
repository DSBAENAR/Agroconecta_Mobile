package com.agroconecta.mobile.data.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.agroconecta.mobile.data.model.CartItem
import com.agroconecta.mobile.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartManager @Inject constructor() {

    var cartItems by mutableStateOf<List<CartItem>>(
        emptyList()
    )

    val totalPrice: Double
        get() = cartItems.sumOf {
            it.totalPrice
        }

    val totalItems: Double
        get() = cartItems.sumOf {
            it.quantity
        }

    fun addToCart(
        product: Product,
        quantity: Double = 1.0
    ) {

        val existing = cartItems.find {
            it.product.id == product.id
        }

        cartItems = if (existing != null) {

            cartItems.map { item ->

                if (item.product.id == product.id) {

                    val newQuantity =
                        item.quantity + quantity

                    item.copy(
                        quantity = newQuantity,
                        totalPrice =
                            newQuantity * item.product.price
                    )

                } else {
                    item
                }
            }

        } else {

            cartItems + CartItem(
                product = product,
                quantity = quantity,
                totalPrice = quantity * product.price
            )
        }
    }

    fun removeFromCart(
        productId: Int
    ) {

        cartItems = cartItems.filterNot {
            it.product.id == productId
        }
    }

    fun increaseQuantity(
        productId: Int,
        amount: Double = 0.5
    ) {

        cartItems = cartItems.map { item ->

            if (item.product.id == productId) {

                val newQuantity =
                    item.quantity + amount

                item.copy(
                    quantity = newQuantity,
                    totalPrice =
                        newQuantity * item.product.price
                )

            } else {
                item
            }
        }
    }

    fun decreaseQuantity(
        productId: Int,
        amount: Double = 0.5
    ) {

        cartItems = cartItems.mapNotNull { item ->

            if (item.product.id == productId) {

                val newQuantity =
                    item.quantity - amount

                if (newQuantity <= 0.0) {

                    null

                } else {

                    item.copy(
                        quantity = newQuantity,
                        totalPrice =
                            newQuantity * item.product.price
                    )
                }

            } else {
                item
            }
        }
    }

    fun updateQuantity(
        productId: Int,
        quantity: Double
    ) {

        cartItems = cartItems.map { item ->

            if (item.product.id == productId) {

                item.copy(
                    quantity = quantity,
                    totalPrice =
                        quantity * item.product.price
                )

            } else {
                item
            }
        }
    }

    fun clearCart() {

        cartItems = emptyList()
    }
}