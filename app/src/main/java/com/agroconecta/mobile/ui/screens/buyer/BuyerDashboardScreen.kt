package com.agroconecta.mobile.ui.screens.buyer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.model.Purchase
import com.agroconecta.mobile.data.model.PurchaseStatus
import com.agroconecta.mobile.ui.theme.AgroConectaTheme
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.GrayDark
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenDark
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.OrangeLight
import com.agroconecta.mobile.ui.theme.StatusDelivered
import com.agroconecta.mobile.ui.theme.StatusInTransit
import com.agroconecta.mobile.ui.theme.StatusPending
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Sample data
// ---------------------------------------------------------------------------

private val samplePurchases = listOf(
    Purchase(
        id = "1",
        productName = "Tomates Cherry",
        sellerName = "Juan Pérez",
        quantity = 50,
        unit = "kg",
        totalPrice = 175_000,
        status = PurchaseStatus.DELIVERED
    ),
    Purchase(
        id = "2",
        productName = "Lechuga Orgánica",
        sellerName = "María González",
        quantity = 30,
        unit = "kg",
        totalPrice = 84_000,
        status = PurchaseStatus.IN_TRANSIT
    ),
    Purchase(
        id = "3",
        productName = "Papas Criollas",
        sellerName = "Ana Rodríguez",
        quantity = 100,
        unit = "kg",
        totalPrice = 320_000,
        status = PurchaseStatus.PENDING
    )
)

// Each product gets a distinct avatar color so the circle is visually distinct.
private val productAvatarColors = listOf(
    Color(0xFFEF9A9A), // soft red  – Tomates Cherry
    Color(0xFFA5D6A7), // soft green – Lechuga Orgánica
    Color(0xFFFFCC80)  // soft amber – Papas Criollas
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@Composable
fun BuyerDashboardScreen() {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = GrayLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // ----------------------------------------------------------------
            // Header
            // ----------------------------------------------------------------
            DashboardHeader()

            Spacer(modifier = Modifier.height(20.dp))

            // ----------------------------------------------------------------
            // Stat cards row
            // ----------------------------------------------------------------
            StatCardsRow()

            Spacer(modifier = Modifier.height(24.dp))

            // ----------------------------------------------------------------
            // Recent purchases section
            // ----------------------------------------------------------------
            Text(
                text = "Compras recientes",
                style = MaterialTheme.typography.titleLarge,
                color = GrayDark,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            samplePurchases.forEachIndexed { index, purchase ->
                PurchaseCard(
                    purchase = purchase,
                    avatarColor = productAvatarColors.getOrElse(index) { GrayMedium }
                )
                if (index < samplePurchases.lastIndex) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Bottom breathing room so content isn't flush against nav bar
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Header composable
// ---------------------------------------------------------------------------

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

// ---------------------------------------------------------------------------
// Stat cards row
// ---------------------------------------------------------------------------

@Composable
private fun StatCardsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            value = "8",
            label = "Compras",
            valueColor = GreenPrimary,
            backgroundColor = GreenLight,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            value = "3",
            label = "Pendientes",
            valueColor = GreenPrimary,
            backgroundColor = Color(0xFFFFFDE7), // light yellow
            modifier = Modifier.weight(1f)
        )
        StatCard(
            value = "$1.2M",
            label = "Gastado",
            valueColor = GrayDark,
            backgroundColor = Color(0xFFEEEEEE), // light gray
            modifier = Modifier.weight(1f)
        )
    }
}

// ---------------------------------------------------------------------------
// Individual stat card
// ---------------------------------------------------------------------------

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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium,
                maxLines = 1
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Purchase card
// ---------------------------------------------------------------------------

@Composable
private fun PurchaseCard(
    purchase: Purchase,
    avatarColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product color avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(avatarColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product details – takes remaining space before the badge
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = purchase.productName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = GrayDark
                )
                Text(
                    text = "${purchase.sellerName} · ${purchase.quantity} ${purchase.unit} · ${purchase.totalPrice.toFormattedPrice()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = GrayMedium,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Status badge
            StatusBadge(status = purchase.status)
        }
    }
}

// ---------------------------------------------------------------------------
// Status badge
// ---------------------------------------------------------------------------

@Composable
private fun StatusBadge(status: PurchaseStatus) {
    val (label, textColor, bgColor) = when (status) {
        PurchaseStatus.DELIVERED -> Triple(
            "Entregado",
            StatusDelivered,
            StatusDelivered.copy(alpha = 0.12f)
        )
        PurchaseStatus.IN_TRANSIT -> Triple(
            "En tránsito",
            StatusInTransit,
            StatusInTransit.copy(alpha = 0.12f)
        )
        PurchaseStatus.PENDING -> Triple(
            "Pendiente",
            StatusPending,
            OrangeLight
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor,
            fontSize = 11.sp
        )
    }
}

// ---------------------------------------------------------------------------
// Extension: format Int price as Colombian peso string (e.g. 175000 -> $175,000)
// ---------------------------------------------------------------------------

private fun Int.toFormattedPrice(): String {
    val formatted = this.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
    return "$$formatted"
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BuyerDashboardScreenPreview() {
    AgroConectaTheme {
        BuyerDashboardScreen()
    }
}
