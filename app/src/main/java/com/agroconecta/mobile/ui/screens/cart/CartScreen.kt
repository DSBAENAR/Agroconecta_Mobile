package com.agroconecta.mobile.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.viewmodel.CartViewModel
import kotlin.math.roundToInt

@Composable
fun CartScreen(
    onCheckoutClick: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val items = cartViewModel.cartItems
    val total = cartViewModel.totalPrice

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = items,
                key = { it.product.id }
            ) { item ->

                var quantityText by remember(item.quantity) {
                    mutableStateOf(
                        item.quantity.toString()
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = item.product.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Precio por ${item.product.unit}: $" +
                                    item.product.price.roundToInt()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = quantityText,

                            onValueChange = {

                                quantityText = it

                                val quantity =
                                    it.toDoubleOrNull()

                                if (
                                    quantity != null &&
                                    quantity > 0
                                ) {

                                    cartViewModel.updateQuantity(
                                        productId = item.product.id,
                                        quantity = quantity
                                    )
                                }
                            },

                            modifier = Modifier.fillMaxWidth(),

                            label = {
                                Text("Cantidad")
                            },

                            supportingText = {

                                Text(
                                    text =
                                        "0.1 kg = 100 gramos"
                                )
                            },

                            keyboardOptions =
                                KeyboardOptions(
                                    keyboardType =
                                        KeyboardType.Decimal
                                ),

                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text =
                                "Pedido mínimo: ${
                                    formatQuantity(
                                        item.product.minOrder
                                    )
                                } ${item.product.unit}"
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text =
                                "Disponible: ${
                                    formatQuantity(
                                        item.product.available
                                    )
                                } ${item.product.unit}"
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Subtotal: $" +
                                    item.totalPrice.roundToInt(),

                            color = GreenPrimary,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TextButton(
                            onClick = {

                                cartViewModel.removeFromCart(
                                    item.product.id
                                )
                            }
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Delete,

                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(6.dp)
                            )

                            Text(
                                text = "Eliminar"
                            )
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
                    horizontalArrangement =
                        Arrangement.SpaceBetween
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
                    onClick = onCheckoutClick,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = GreenPrimary
                        )
                ) {

                    Text(
                        text = "Proceder al pago"
                    )
                }
            }
        }
    }
}

private fun formatQuantity(
    quantity: Double
): String {

    return if (
        quantity % 1.0 == 0.0
    ) {
        quantity.toInt().toString()
    } else {
        quantity.toString()
    }
}