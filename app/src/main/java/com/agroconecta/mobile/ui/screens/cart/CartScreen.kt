package com.agroconecta.mobile.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.viewmodel.CartViewModel
import com.agroconecta.mobile.ui.viewmodel.PurchaseViewModel
import java.time.LocalDateTime

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    purchaseViewModel: PurchaseViewModel = hiltViewModel()
) {

    val items = cartViewModel.cartItems
    val total = cartViewModel.totalPrice
    val session = SessionManager.session

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Carrito",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (items.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Tu carrito está vacío"
                )
            }

            return
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(items) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {

                        Text(
                            text = item.product.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Precio unitario: $${item.product.price.toInt()}"
                        )

                        Text(
                            text = "Subtotal: $${item.totalPrice.toInt()}",
                            color = GreenPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                IconButton(
                                    onClick = {
                                        cartViewModel.decreaseQuantity(item.product.id)
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = null
                                    )
                                }

                                Text(
                                    text = item.quantity.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                IconButton(
                                    onClick = {
                                        cartViewModel.increaseQuantity(item.product.id)
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    cartViewModel.removeFromCart(item.product.id)
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Total"
                    )

                    Text(
                        text = "$${total.toInt()}",
                        color = GreenPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {

                    Text("Finalizar compra")
                }
            }
        }
    }
}