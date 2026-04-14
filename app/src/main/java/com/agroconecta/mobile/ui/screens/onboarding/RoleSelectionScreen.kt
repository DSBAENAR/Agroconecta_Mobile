package com.agroconecta.mobile.ui.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.OrangeAccent
import com.agroconecta.mobile.ui.theme.OrangeLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.White
import com.agroconecta.mobile.ui.theme.Black

@Composable
fun RoleSelectionScreen(
    onBuyerClick: () -> Unit,
    onSellerClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "\uD83D\uDC4B",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Qué deseas hacer?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecciona tu rol para personalizar tu experiencia",
                style = MaterialTheme.typography.bodyLarge,
                color = GrayMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Buyer card
            RoleCard(
                emoji = "\uD83D\uDED2",
                title = "Quiero comprar",
                description = "Encuentra productos frescos directo del agricultor",
                borderColor = GreenPrimary,
                backgroundColor = GreenLight,
                onClick = onBuyerClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Seller card
            RoleCard(
                emoji = "\uD83C\uDF3E",
                title = "Quiero vender",
                description = "Vende tus productos a compradores de todo el país",
                borderColor = OrangeAccent,
                backgroundColor = OrangeLight,
                onClick = onSellerClick
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun RoleCard(
    emoji: String,
    title: String,
    description: String,
    borderColor: androidx.compose.ui.graphics.Color,
    backgroundColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                fontSize = 44.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = GrayMedium
                )
            }

            Text(
                text = "→",
                fontSize = 24.sp,
                color = borderColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
