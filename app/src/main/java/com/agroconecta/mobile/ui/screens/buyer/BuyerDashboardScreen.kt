package com.agroconecta.mobile.ui.screens.buyer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.PurchaseViewModel

@Composable
fun BuyerDashboardScreen(
    viewModel: PurchaseViewModel = hiltViewModel()
) {
    val purchases = viewModel.purchases
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (purchases.isEmpty()) {
            viewModel.loadPurchases()
        }
    }

    val totalPurchases = purchases.size
    val pendingPurchases = purchases.count {
        it.status == PurchaseStatus.PENDING
    }
    val totalSpent = purchases.sumOf { it.totalPrice }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = GrayLight
    ) {
        if (viewModel.isLoading && purchases.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                DashboardHeader()

                Spacer(modifier = Modifier.height(24.dp))

                StatCardsRow(
                    totalPurchases = totalPurchases,
                    pendingPurchases = pendingPurchases,
                    totalSpent = totalSpent
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Compras recientes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = GrayDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (purchases.isEmpty()) {
                    EmptyPurchasesState()
                } else {
                    purchases.forEachIndexed { index, purchase ->
                        PurchaseCard(
                            purchase = purchase,
                            avatarColor = getProductColor(index)
                        )

                        if (index < purchases.lastIndex) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardHeader() {
    Column {
        Text(
            text = "Mis Compras",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = GrayDark
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Gestiona tus compras y proveedores",
            style = MaterialTheme.typography.bodyMedium,
            color = GrayMedium
        )
    }
}

@Composable
private fun StatCardsRow(
    totalPurchases: Int,
    pendingPurchases: Int,
    totalSpent: Double
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            value = totalPurchases.toString(),
            label = "Compras",
            valueColor = GreenPrimary,
            backgroundColor = GreenLight,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            value = pendingPurchases.toString(),
            label = "Pendientes",
            valueColor = StatusPending,
            backgroundColor = OrangeLight,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            value = totalSpent.toFormattedPrice(),
            label = "Gastado",
            valueColor = GrayDark,
            backgroundColor = White,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    valueColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }
    }
}

@Composable
private fun PurchaseCard(
    purchase: Purchase,
    avatarColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(avatarColor)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Producto #${purchase.productId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GrayDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Agricultor #${purchase.farmerId}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GrayMedium
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${purchase.quantity} unidades",
                    style = MaterialTheme.typography.bodySmall,
                    color = GrayMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = purchase.totalPrice.toFormattedPrice(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            }

            StatusBadge(status = purchase.status)
        }
    }
}

@Composable
private fun StatusBadge(
    status: PurchaseStatus
) {
    val (text, textColor, backgroundColor) = when (status) {
        PurchaseStatus.DELIVERED -> Triple(
            "Entregado",
            StatusDelivered,
            StatusDelivered.copy(alpha = 0.15f)
        )

        PurchaseStatus.IN_TRANSIT -> Triple(
            "En tránsito",
            StatusInTransit,
            StatusInTransit.copy(alpha = 0.15f)
        )

        PurchaseStatus.PENDING -> Triple(
            "Pendiente",
            StatusPending,
            OrangeLight
        )

        PurchaseStatus.CANCELLED -> Triple(
            "Cancelado",
            Color(0xFFD32F2F),
            Color(0xFFFFEBEE)
        )

        PurchaseStatus.UNKNOWN -> Triple(
            "Desconocido",
            GrayMedium,
            GrayMedium.copy(alpha = 0.15f)
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun EmptyPurchasesState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aún no has realizado compras.",
            style = MaterialTheme.typography.bodyLarge,
            color = GrayMedium
        )
    }
}

private fun getProductColor(index: Int): Color {
    val colors = listOf(
        Color(0xFFEF9A9A),
        Color(0xFFA5D6A7),
        Color(0xFFFFCC80),
        Color(0xFF90CAF9),
        Color(0xFFCE93D8)
    )

    return colors[index % colors.size]
}

private fun Number.toFormattedPrice(): String {
    return "$${"%,.0f".format(this.toDouble())}"
}