package com.agroconecta.mobile.ui.screens.buyer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.PurchaseViewModel

@Composable
fun BuyerDashboardScreen(
    viewModel: PurchaseViewModel = hiltViewModel()
) {

    val session = SessionManager.session
    val purchases = viewModel.purchases
    val scrollState = rememberScrollState()

    // ✅ Nombre real con fallback seguro
    val userName = remember(session) {
        when {
            session == null -> "Usuario"
            session.name.isNotBlank() -> session.name
            session.email.isNotBlank() -> session.email.substringBefore("@")
            else -> "Usuario"
        }
    }

    LaunchedEffect(session?.userId) {
        session?.userId?.let {
            viewModel.loadPurchasesByBuyer(it)
        }
    }

    val total = purchases.size
    val pending = purchases.count { it.status == PurchaseStatus.PENDING }
    val spent = purchases.sumOf { it.totalPrice }

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

                BuyerHeader(userName)

                Spacer(Modifier.height(20.dp))

                StatCardsRow(
                    total = total,
                    pending = pending,
                    spent = spent
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    "Tus compras",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = GrayDark
                )

                Spacer(Modifier.height(12.dp))

                if (purchases.isEmpty()) {
                    EmptyState()
                } else {
                    purchases.forEach { purchase ->
                        PurchaseCard(purchase)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

/* ---------------- HEADER ---------------- */

@Composable
private fun BuyerHeader(name: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(GreenPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.uppercase() ?: "U",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text("Bienvenido,", color = GrayMedium)
            Text(name, fontWeight = FontWeight.Bold)
        }
    }
}

/* ---------------- STATS ---------------- */

@Composable
private fun StatCardsRow(
    total: Int,
    pending: Int,
    spent: Double
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        StatCard(
            modifier = Modifier.weight(1f),
            title = "Compras",
            value = total.toString(),
            color = GreenPrimary,
            bg = GreenLight
        )

        StatCard(
            modifier = Modifier.weight(1f),
            title = "Pendientes",
            value = pending.toString(),
            color = Color(0xFFFF9800),
            bg = Color(0xFFFFF3E0)
        )

        StatCard(
            modifier = Modifier.weight(1f),
            title = "Gastado",
            value = "$${spent.toInt()}",
            color = GrayDark,
            bg = Color.White
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    color: Color,
    bg: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, color = color)
            Text(title, fontSize = 12.sp, color = GrayMedium)
        }
    }
}

/* ---------------- PURCHASE CARD ---------------- */

@Composable
private fun PurchaseCard(purchase: Purchase) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary.copy(alpha = 0.2f))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    purchase.productName,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "${purchase.quantity} unidades",
                    fontSize = 12.sp,
                    color = GrayMedium
                )

                Text(
                    "$${purchase.totalPrice.toInt()}",
                    color = GreenPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            StatusBadge(purchase.status)
        }
    }
}

/* ---------------- STATUS ---------------- */

@Composable
private fun StatusBadge(status: PurchaseStatus) {

    val (text, color, bg) = when (status) {
        PurchaseStatus.DELIVERED ->
            Triple("Entregado", Color(0xFF2E7D32), Color(0xFFE8F5E9))

        PurchaseStatus.IN_TRANSIT ->
            Triple("En camino", Color(0xFF1565C0), Color(0xFFE3F2FD))

        PurchaseStatus.PENDING ->
            Triple("Pendiente", Color(0xFFFF9800), Color(0xFFFFF3E0))

        PurchaseStatus.CANCELLED ->
            Triple("Cancelado", Color.Red, Color(0xFFFFEBEE))

        PurchaseStatus.UNKNOWN ->
            Triple("?", GrayMedium, Color.LightGray)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 11.sp, color = color)
    }
}

/* ---------------- EMPTY ---------------- */

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("🛒", fontSize = 40.sp)

            Spacer(Modifier.height(8.dp))

            Text("No tienes compras aún", fontWeight = FontWeight.Bold)

            Text(
                "Explora productos del marketplace",
                color = GrayMedium,
                fontSize = 13.sp
            )
        }
    }
}