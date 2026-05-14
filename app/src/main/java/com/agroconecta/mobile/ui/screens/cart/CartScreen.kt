package com.agroconecta.mobile.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import kotlin.math.roundToInt

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

            items(
                items = items,
                key = { it.product.id }
            ) { item ->

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
                            text = "Precio por ${item.product.unit}: $" +
                                    "${item.product.price.roundToInt()}"
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Cantidad: ${
                                formatQuantity(item.quantity)
                            } ${item.product.unit}"
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Subtotal: $" +
                                    "${item.totalPrice.roundToInt()}",
                            color = GreenPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

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
                                        cartViewModel.decreaseQuantity(
                                            productId = item.product.id
                                        )
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Disminuir"
                                    )
                                }

                                Text(
                                    text = formatQuantity(item.quantity),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                IconButton(
                                    onClick = {
                                        cartViewModel.increaseQuantity(
                                            productId = item.product.id
                                        )
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Aumentar"
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    cartViewModel.removeFromCart(
                                        item.product.id
                                    )
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar"
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
                        text = "$${total.roundToInt()}",
                        color = GreenPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        val buyerId = session?.userId
                            ?: return@Button

                        val purchases = items.map { item ->

                            Purchase(
                                id = (0..99999).random(),

                                productId = item.product.id,

                                productName = item.product.name,

                                farmerId = item.product.farmerId,

                                buyerId = buyerId,

                                quantity = item.quantity,

                                price = item.product.price,

                                totalPrice = item.totalPrice,

                                status = PurchaseStatus.PENDING,

                                createdAt = LocalDateTime.now()
                            )
                        }

                        purchaseViewModel.createPurchases(
                            purchases
                        )

                        cartViewModel.clearCart()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {

                    Text(
                        text = "Finalizar compra"
                    )
                }
            }
        }
    }
}

private fun formatQuantity(
    quantity: Double
): String {

    return if (quantity % 1.0 == 0.0) {
        quantity.toInt().toString()
    } else {
        quantity.toString()
    }
}