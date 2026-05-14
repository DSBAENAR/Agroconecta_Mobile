package com.agroconecta.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.agroconecta.mobile.data.cart.CartManager
import com.agroconecta.mobile.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartManager: CartManager
) : ViewModel() {

    val cartItems
        get() = cartManager.cartItems

    val totalPrice: Double
        get() = cartManager.totalPrice

    val totalItems: Double
        get() = cartManager.totalItems

    fun addToCart(
        product: Product,
        quantity: Double = 1.0
    ) {
        cartManager.addToCart(product, quantity)
    }

    fun removeFromCart(productId: Int) {
        cartManager.removeFromCart(productId)
    }

    fun increaseQuantity(productId: Int) {
        cartManager.increaseQuantity(productId)
    }

    fun decreaseQuantity(productId: Int) {
        cartManager.decreaseQuantity(productId)
    }

    fun clearCart() {
        cartManager.clearCart()
    }
}