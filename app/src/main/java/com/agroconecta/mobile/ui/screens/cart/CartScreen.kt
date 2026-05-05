package com.agroconecta.mobile.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.viewmodel.CartViewModel
import com.agroconecta.mobile.ui.viewmodel.PurchaseViewModel
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.data.session.SessionManager
import java.time.LocalDateTime

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    purchaseViewModel: PurchaseViewModel = hiltViewModel()
) {

    val items = cartViewModel.cartItems
    val total = cartViewModel.totalPrice
    val session = SessionManager.session

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Carrito", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        if (items.isEmpty()) {
            Text("Tu carrito está vacío")
            return
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.product.name)
                            Text("Cantidad: ${item.quantity}")
                            Text("$${item.totalPrice.toInt()}")
                        }

                        Row {
                            IconButton(onClick = {
                                cartViewModel.decreaseQuantity(item.product.id)
                            }) {
                                Icon(Icons.Default.Remove, null)
                            }

                            IconButton(onClick = {
                                cartViewModel.increaseQuantity(item.product.id)
                            }) {
                                Icon(Icons.Default.Add, null)
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Total: $${total.toInt()}")

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {

                val buyerId = session?.userId ?: return@Button

                val purchases = items.map {
                    Purchase(
                        id = (0..99999).random(),
                        productId = it.product.id,
                        productName = it.product.name,
                        farmerId = it.product.farmerId,
                        buyerId = buyerId,
                        quantity = it.quantity.toFloat(),
                        price = it.product.price.toFloat(),
                        totalPrice = it.totalPrice,
                        status = PurchaseStatus.PENDING,
                        createdAt = LocalDateTime.now()
                    )
                }

                purchaseViewModel.createPurchases(purchases)

                cartViewModel.clearCart()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar compra")
        }
    }
}