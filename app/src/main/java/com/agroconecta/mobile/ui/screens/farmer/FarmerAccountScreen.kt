package com.agroconecta.mobile.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.theme.*

// ---------------------------------------------------------------------------
// Mock data
// ---------------------------------------------------------------------------

private data class AccountTransaction(
    val id: Int,
    val description: String,
    val amount: Double,
    val date: String,
    val status: TransactionStatus
)

private enum class TransactionStatus { RECEIVED, PENDING, WITHDRAWN }

private val mockTransactions = listOf(
    AccountTransaction(1, "Venta · Tomates Cherry", 350_000.0, "15 May 2026", TransactionStatus.RECEIVED),
    AccountTransaction(2, "Venta · Café Pergamino", 520_000.0, "13 May 2026", TransactionStatus.RECEIVED),
    AccountTransaction(3, "Venta · Maíz Dulce", 280_000.0, "10 May 2026", TransactionStatus.RECEIVED),
    AccountTransaction(4, "Venta · Cacao Premium", 410_000.0, "08 May 2026", TransactionStatus.RECEIVED),
    AccountTransaction(5, "Retiro a Bancolombia", -850_000.0, "05 May 2026", TransactionStatus.WITHDRAWN),
    AccountTransaction(6, "Venta · Naranja Valencia", 190_000.0, "02 May 2026", TransactionStatus.RECEIVED),
    AccountTransaction(7, "Venta · Limón Tahití", 245_000.0, "28 Abr 2026", TransactionStatus.RECEIVED),
    AccountTransaction(8, "En procesamiento", 310_000.0, "Pendiente", TransactionStatus.PENDING),
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@Composable
fun FarmerAccountScreen(onBackClick: () -> Unit) {
    val farmerName = SessionManager.session?.name ?: "Agricultor"

    val available = mockTransactions
        .filter { it.status == TransactionStatus.RECEIVED || it.status == TransactionStatus.WITHDRAWN }
        .sumOf { it.amount }

    val pending = mockTransactions
        .filter { it.status == TransactionStatus.PENDING }
        .sumOf { it.amount }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF7F8FA)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AccountHeader(onBackClick = onBackClick)

            Spacer(Modifier.height(4.dp))

            BalanceCard(available = available, pending = pending)

            Spacer(Modifier.height(20.dp))

            AccountInfoCard(farmerName = farmerName)

            Spacer(Modifier.height(20.dp))

            TransactionHistorySection(
                transactions = mockTransactions.sortedBy {
                    if (it.status == TransactionStatus.PENDING) 0 else 1
                }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun AccountHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GreenLight)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = "Mi Cuenta",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                text = "Saldo y movimientos",
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Balance card
// ---------------------------------------------------------------------------

@Composable
private fun BalanceCard(available: Double, pending: Double) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(GreenPrimary, GreenDark)
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = White.copy(alpha = 0.80f)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "$ ${formatCOP(available)}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing = (-0.5).sp
            )

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(color = White.copy(alpha = 0.20f))

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BalanceStat(
                    label = "En procesamiento",
                    value = "$ ${formatCOP(pending)}",
                    icon = Icons.Default.HourglassEmpty
                )
                BalanceStat(
                    label = "Total recibido",
                    value = "$ ${formatCOP(mockTransactions.filter { it.status == TransactionStatus.RECEIVED }.sumOf { it.amount })}",
                    icon = Icons.Default.TrendingUp
                )
            }
        }
    }
}

@Composable
private fun BalanceStat(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White.copy(alpha = 0.75f),
            modifier = Modifier.size(16.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = White.copy(alpha = 0.75f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Account info card
// ---------------------------------------------------------------------------

@Composable
private fun AccountInfoCard(farmerName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Información de cuenta",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Black
        )

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AccountInfoRow(
                    icon = Icons.Default.AccountBalance,
                    iconBg = GreenLight,
                    iconTint = GreenPrimary,
                    label = "Banco",
                    value = "Bancolombia"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = GrayBorder)

                AccountInfoRow(
                    icon = Icons.Default.CreditCard,
                    iconBg = Color(0xFFE8EAF6),
                    iconTint = Color(0xFF3F51B5),
                    label = "Tipo de cuenta",
                    value = "Cuenta de Ahorros"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = GrayBorder)

                AccountInfoRow(
                    icon = Icons.Default.Numbers,
                    iconBg = OrangeLight,
                    iconTint = OrangeAccent,
                    label = "Número",
                    value = "**** **** **** 4521"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = GrayBorder)

                AccountInfoRow(
                    icon = Icons.Default.Person,
                    iconBg = GreenLight,
                    iconTint = GreenPrimary,
                    label = "Titular",
                    value = farmerName
                )
            }
        }
    }
}

@Composable
private fun AccountInfoRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = GrayMedium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Transaction history
// ---------------------------------------------------------------------------

@Composable
private fun TransactionHistorySection(transactions: List<AccountTransaction>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Historial de pagos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Black
        )

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                transactions.forEachIndexed { index, tx ->
                    TransactionRow(transaction = tx)
                    if (index < transactions.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = GrayBorder
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(transaction: AccountTransaction) {
    val (iconVector, iconBg, iconTint, amountColor, amountPrefix) = when (transaction.status) {
        TransactionStatus.RECEIVED -> TransactionStyle(
            icon = Icons.Default.ArrowDownward,
            iconBg = Color(0xFFDCF5E0),
            iconTint = GreenPrimary,
            amountColor = GreenPrimary,
            prefix = "+"
        )
        TransactionStatus.WITHDRAWN -> TransactionStyle(
            icon = Icons.Default.ArrowUpward,
            iconBg = OrangeLight,
            iconTint = OrangeAccent,
            amountColor = OrangeAccent,
            prefix = ""
        )
        TransactionStatus.PENDING -> TransactionStyle(
            icon = Icons.Default.HourglassEmpty,
            iconBg = Color(0xFFE8EAF6),
            iconTint = Color(0xFF3F51B5),
            amountColor = Color(0xFF3F51B5),
            prefix = "+"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = transaction.date,
                style = MaterialTheme.typography.labelSmall,
                color = GrayMedium
            )
        }

        Text(
            text = "$amountPrefix$ ${formatCOP(transaction.amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = amountColor
        )
    }
}

private data class TransactionStyle(
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val amountColor: Color,
    val prefix: String
)

// ---------------------------------------------------------------------------
// Helper
// ---------------------------------------------------------------------------

private fun formatCOP(amount: Double): String {
    val abs = kotlin.math.abs(amount)
    return "%,.0f".format(abs).replace(",", ".")
}
