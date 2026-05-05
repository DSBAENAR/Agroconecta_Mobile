package com.agroconecta.mobile.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.agroconecta.mobile.data.model.CartItem
import com.agroconecta.mobile.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())
        private set

    val totalPrice: Double
        get() = cartItems.sumOf { it.totalPrice }

    val totalItems: Int
        get() = cartItems.sumOf { it.quantity }

    fun addToCart(product: Product) {
        val existing = cartItems.find { it.product.id == product.id }

        cartItems = if (existing != null) {
            cartItems.map {
                if (it.product.id == product.id) {
                    it.copy(quantity = it.quantity + 1)
                } else it
            }
        } else {
            cartItems + CartItem(product, 1)
        }
    }

    fun removeFromCart(productId: Int) {
        cartItems = cartItems.filterNot { it.product.id == productId }
    }

    fun increaseQuantity(productId: Int) {
        cartItems = cartItems.map {
            if (it.product.id == productId) {
                it.copy(quantity = it.quantity + 1)
            } else it
        }
    }

    fun decreaseQuantity(productId: Int) {
        cartItems = cartItems.mapNotNull {
            if (it.product.id == productId) {
                val newQty = it.quantity - 1
                if (newQty <= 0) null else it.copy(quantity = newQty)
            } else it
        }
    }

    fun clearCart() {
        cartItems = emptyList()
    }
}