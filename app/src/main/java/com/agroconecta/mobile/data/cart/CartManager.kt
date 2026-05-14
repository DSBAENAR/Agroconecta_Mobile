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

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())

    val totalPrice: Double
        get() = cartItems.sumOf { it.totalPrice }

    val totalItems: Double
        get() = cartItems.sumOf { it.quantity }

    fun addToCart(
        product: Product,
        quantity: Double = 1.0
    ) {

        val existing = cartItems.find {
            it.product.id == product.id
        }

        cartItems = if (existing != null) {

            cartItems.map {

                if (it.product.id == product.id) {

                    it.copy(
                        quantity = it.quantity + quantity
                    )

                } else {
                    it
                }
            }

        } else {

            cartItems + CartItem(
                product = product,
                quantity = quantity
            )
        }
    }

    fun removeFromCart(productId: Int) {

        cartItems = cartItems.filterNot {
            it.product.id == productId
        }
    }

    fun increaseQuantity(
        productId: Int,
        amount: Double = 0.5
    ) {

        cartItems = cartItems.map {

            if (it.product.id == productId) {

                it.copy(
                    quantity = it.quantity + amount
                )

            } else {
                it
            }
        }
    }

    fun decreaseQuantity(
        productId: Int,
        amount: Double = 0.5
    ) {

        cartItems = cartItems.mapNotNull {

            if (it.product.id == productId) {

                val newQty = it.quantity - amount

                if (newQty <= 0.0) {
                    null
                } else {
                    it.copy(quantity = newQty)
                }

            } else {
                it
            }
        }
    }

    fun clearCart() {

        cartItems = emptyList()
    }
}