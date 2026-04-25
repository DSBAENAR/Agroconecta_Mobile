package com.agroconecta.mobile.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.data.model.ProductStatus
import com.agroconecta.mobile.ui.theme.*

@Composable
fun FarmerDashboardScreen(
    onAddProductClick: () -> Unit,
    onAnalysisClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {

        Text(
            text = "Dashboard Agricultor",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                value = "12",
                label = "Productos",
                icon = Icons.Default.Inventory2,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                value = "5",
                label = "Pendientes",
                icon = Icons.Default.Agriculture,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                value = "3.2M",
                label = "Ventas",
                icon = Icons.Default.BarChart,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddProductClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar producto")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAnalysisClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver análisis IA")
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = GreenLight)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }
    }
}

@Composable
private fun StatusBadge(status: ProductStatus) {
    val (label, textColor, backgroundColor) = when (status) {
        ProductStatus.ACTIVE -> Triple(
            "Activo",
            GreenPrimary,
            Color(0xFFDCF5E0)
        )

        ProductStatus.SOLD -> Triple(
            "Vendido",
            Color(0xFF3F51B5),
            Color(0xFFE8EAF6)
        )

        ProductStatus.PAUSED -> Triple(
            "Pausado",
            Color(0xFFFF9800),
            Color(0xFFFFF3E0)
        )
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelSmall
        )
    }
}