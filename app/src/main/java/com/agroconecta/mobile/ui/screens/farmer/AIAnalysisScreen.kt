package com.agroconecta.mobile.ui.screens.farmer

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.model.*
import com.agroconecta.mobile.ui.theme.*

// ---------------------------------------------------------------------------
// Internal UI models
// ---------------------------------------------------------------------------

private data class InsightCardData(
    val icon: ImageVector,
    val iconTint: Color,
    val iconBg: Color,
    val title: String,
    val description: String
)

private data class DemandCardData(
    val product: String,
    val levelLabel: String,
    val indicatorColor: Color,
    val pillTextColor: Color,
    val pillBgColor: Color
)

// ---------------------------------------------------------------------------
// Static sample data
// ---------------------------------------------------------------------------

private val sampleInsights = listOf(
    InsightCardData(
        icon = Icons.Filled.TrendingUp,
        iconTint = OrangeAccent,
        iconBg = OrangeLight,
        title = "Sube precio de Zanahoria",
        description = "Escasez detectada en Bogotá (+12%)"
    ),
    InsightCardData(
        icon = Icons.Filled.CalendarMonth,
        iconTint = OrangeAccent,
        iconBg = OrangeLight,
        title = "Cosecha el jueves",
        description = "3 restaurantes buscan tomate esta semana"
    ),
    InsightCardData(
        icon = Icons.Filled.LocalShipping,
        iconTint = Color(0xFF3F51B5),
        iconBg = Color(0xFFE8EAF6),
        title = "Agrupa envío con vecinos",
        description = "Ruta inteligente ahorra 30% en flete"
    )
)

private val sampleDemand = listOf(
    DemandCardData(
        product = "Tomate",
        levelLabel = "Alta",
        indicatorColor = GreenPrimary,
        pillTextColor = GreenPrimary,
        pillBgColor = Color(0xFFDCF5E0)
    ),
    DemandCardData(
        product = "Papa",
        levelLabel = "Media",
        indicatorColor = OrangeAccent,
        pillTextColor = OrangeAccent,
        pillBgColor = OrangeLight
    ),
    DemandCardData(
        product = "Maíz",
        levelLabel = "Baja",
        indicatorColor = GrayMedium,
        pillTextColor = GrayMedium,
        pillBgColor = GrayLight
    )
)

// ---------------------------------------------------------------------------
// Screen root
// ---------------------------------------------------------------------------

/**
 * AI Analysis screen — "Análisis IA".
 *
 * Displays GPT-4o-powered insights, demand trends, and harvest recommendations
 * for the logged-in farmer.
 */
@Composable
fun AIAnalysisScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 40.dp)
        ) {
            AnalysisHeader()

            Spacer(modifier = Modifier.height(20.dp))

            TopProductHeroCard()

            Spacer(modifier = Modifier.height(28.dp))

            InsightsSection(insights = sampleInsights)

            Spacer(modifier = Modifier.height(28.dp))

            DemandSection(items = sampleDemand)
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun AnalysisHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Análisis IA",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Gpt4oBadge()
        }

        Text(
            text = "Recomendaciones para tus productos",
            style = MaterialTheme.typography.bodyMedium,
            color = GrayMedium
        )
    }
}

@Composable
private fun Gpt4oBadge() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6C47FF), Color(0xFF9C27B0))
                )
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AutoAwesome,
            contentDescription = "Powered by AI",
            tint = White,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = "GPT-4o",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = White,
            letterSpacing = 0.4.sp
        )
    }
}

// ---------------------------------------------------------------------------
// Top product hero card
// ---------------------------------------------------------------------------

@Composable
private fun TopProductHeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GreenPrimary, GreenDark)
                )
            )
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Label
            Text(
                text = "Tu producto más vendido",
                style = MaterialTheme.typography.bodySmall,
                color = GreenLight,
                fontWeight = FontWeight.Medium
            )

            // Name + percentage badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Tomates Cherry",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = White,
                    modifier = Modifier.weight(1f)
                )
                SalesBadge()
            }

            // AI recommendation bullets
            AiBullet(text = "La IA sugiere subir el precio un 5%.")
            AiBullet(text = "La demanda en Bogotá creció esta semana.")

            Spacer(modifier = Modifier.height(2.dp))

            // Bottom highlight in orange
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Agriculture,
                    contentDescription = null,
                    tint = OrangeAccent,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "Tu producto más vendido",
                    style = MaterialTheme.typography.bodySmall,
                    color = OrangeAccent,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun SalesBadge() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(White.copy(alpha = 0.20f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = "+15% ventas",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Composable
private fun AiBullet(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "•",
            fontWeight = FontWeight.Bold,
            color = GreenLight,
            fontSize = 14.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = White.copy(alpha = 0.92f)
        )
    }
}

// ---------------------------------------------------------------------------
// Insights section
// ---------------------------------------------------------------------------

@Composable
private fun InsightsSection(insights: List<InsightCardData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle(text = "Insights de la semana")

        insights.forEach { insight ->
            InsightCard(data = insight)
        }
    }
}

@Composable
private fun InsightCard(data: InsightCardData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GrayLight)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(data.iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = null,
                tint = data.iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = data.description,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Demand section
// ---------------------------------------------------------------------------

@Composable
private fun DemandSection(items: List<DemandCardData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionTitle(text = "Demanda esta semana")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.forEach { item ->
                DemandCard(data = item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun DemandCard(
    data: DemandCardData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(GrayLight)
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Demand level indicator dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(50))
                .background(data.indicatorColor)
        )

        Text(
            text = data.product,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Black
        )

        // Demand level pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(data.pillBgColor)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = data.levelLabel,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = data.pillTextColor
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Shared helpers
// ---------------------------------------------------------------------------

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Black
    )
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true, name = "AI Analysis Screen")
@Composable
private fun AIAnalysisScreenPreview() {
    AgroConectaTheme {
        AIAnalysisScreen()
    }
}
