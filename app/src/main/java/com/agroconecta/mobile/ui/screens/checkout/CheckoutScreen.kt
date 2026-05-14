package com.agroconecta.mobile.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White
import com.agroconecta.mobile.ui.viewmodel.CartViewModel
import com.agroconecta.mobile.ui.viewmodel.PurchaseViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Composable
fun CheckoutScreen(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    purchaseViewModel: PurchaseViewModel = hiltViewModel()
) {

    val items = cartViewModel.cartItems
    val total = cartViewModel.totalPrice
    val session = SessionManager.session

    var cardName by remember {
        mutableStateOf("")
    }

    var cardNumber by remember {
        mutableStateOf("")
    }

    var expiration by remember {
        mutableStateOf("")
    }

    var cvv by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var paymentSuccess by remember {
        mutableStateOf(false)
    }

    val canPay =
        cardName.isNotBlank() &&
                cardNumber.length >= 16 &&
                expiration.length >= 4 &&
                cvv.length >= 3

    LaunchedEffect(paymentSuccess) {

        if (paymentSuccess) {

            delay(1800)

            onSuccess()
        }
    }

    Scaffold(
        containerColor = GrayLight
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = GreenPrimary
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Checkout",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = GreenPrimary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Pago seguro (SIMULACIÓN)",
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text =
                            "Esta pantalla es únicamente una simulación UI. " +
                                    "No se procesa ningún pago real ni se almacenan datos.",
                        color = GrayMedium,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Datos de pago",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = cardName,
                        onValueChange = {
                            cardName = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Nombre del titular")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = {

                            if (it.length <= 16) {

                                cardNumber = it.filter { char ->
                                    char.isDigit()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Número de tarjeta")
                        },
                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.CreditCard,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        OutlinedTextField(
                            value = expiration,
                            onValueChange = {

                                if (it.length <= 5) {
                                    expiration = it
                                }
                            },
                            modifier = Modifier.weight(1f),
                            label = {
                                Text("MM/YY")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = cvv,
                            onValueChange = {

                                if (it.length <= 4) {

                                    cvv = it.filter { char ->
                                        char.isDigit()
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            label = {
                                Text("CVV")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    items.forEach { item ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text = item.product.name,
                                    fontWeight =
                                        FontWeight.SemiBold
                                )

                                Text(
                                    text =
                                        "${formatQuantity(item.quantity)} " +
                                                item.product.unit,
                                    color = GrayMedium,
                                    style =
                                        MaterialTheme.typography.bodySmall
                                )
                            }

                            Text(
                                text =
                                    "$${item.totalPrice.roundToInt()}",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "TOTAL",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "$${total.roundToInt()}",
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {

                    if (isLoading) return@Button

                    val buyerId =
                        session?.userId ?: return@Button

                    isLoading = true

                    val purchases = items.map { item ->

                        Purchase(
                            id = (0..999999).random(),

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

                    paymentSuccess = true
                },
                enabled = canPay && !paymentSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                )
            ) {

                when {

                    isLoading && !paymentSuccess -> {

                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = White
                        )
                    }

                    paymentSuccess -> {

                        Text(
                            text = "Pago aprobado",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    else -> {

                        Text(
                            text = "Pagar ahora",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Cancelar"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
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