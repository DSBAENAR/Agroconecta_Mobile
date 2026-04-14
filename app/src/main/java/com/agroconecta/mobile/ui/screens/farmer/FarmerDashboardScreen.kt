package com.agroconecta.mobile.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.data.model.*
import com.agroconecta.mobile.ui.theme.*

// ---------------------------------------------------------------------------
// Internal UI model
// ---------------------------------------------------------------------------

private data class StatCardData(
    val value: String,
    val label: String,
    val valueColor: Color,
    val backgroundColor: Color,
    val icon: ImageVector
)

// ---------------------------------------------------------------------------
// Tab definition
// ---------------------------------------------------------------------------

private enum class DashboardTab(val label: String, val icon: ImageVector) {
    PRODUCTOS("Productos", Icons.Filled.Inventory2),
    PEDIDOS("Pedidos",   Icons.Filled.ListAlt),
    ANALISIS("Análisis", Icons.Filled.BarChart)
}

// ---------------------------------------------------------------------------
// Internal UI model for sales
// ---------------------------------------------------------------------------

private data class RecentSale(
    val buyerName: String,
    val buyerInitials: String,
    val productName: String,
    val quantity: Int,
    val unit: String,
    val totalPrice: Int,
    val date: String,
    val isNew: Boolean = false
)

private val sampleRecentSales = listOf(
    RecentSale("Carlos Ruiz",    "CR", "Tomates Cherry",   50,  "kg", 175_000, "Hoy, 9:30 am",     isNew = true),
    RecentSale("María González", "MG", "Papas Criollas",   100, "kg", 320_000, "Hoy, 7:15 am",     isNew = true),
    RecentSale("Luis Herrera",   "LH", "Maíz Amarillo",    200, "kg", 360_000, "Ayer, 4:00 pm"),
    RecentSale("Ana Rodríguez",  "AR", "Tomates Cherry",   30,  "kg", 105_000, "Ayer, 11:00 am"),
    RecentSale("Pedro Castro",   "PC", "Papas Criollas",   80,  "kg", 256_000, "Hace 2 días"),
)

// ---------------------------------------------------------------------------
// Static sample data
// ---------------------------------------------------------------------------

private val sampleProducts = listOf(
    Product(
        id = "1",
        name = "Tomates Cherry",
        category = "Verduras",
        price = 3_500,
        unit = "kg",
        available = 500,
        minOrder = 1,
        location = "Bogotá",
        rating = 4.8f,
        farmerId = "f1",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "2",
        name = "Papas Criollas",
        category = "Tubérculos",
        price = 3_200,
        unit = "kg",
        available = 1_000,
        minOrder = 1,
        location = "Bogotá",
        rating = 4.5f,
        farmerId = "f1",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "3",
        name = "Maíz Amarillo",
        category = "Cereales",
        price = 1_800,
        unit = "kg",
        available = 2_000,
        minOrder = 1,
        location = "Bogotá",
        rating = 4.2f,
        farmerId = "f1",
        status = ProductStatus.SOLD
    )
)

// ---------------------------------------------------------------------------
// Screen root
// ---------------------------------------------------------------------------

/**
 * Farmer dashboard screen — "Mi Dashboard".
 *
 * @param onAddProductClick Triggered when the "+ Agregar Producto" CTA is tapped.
 * @param onAnalysisClick   Triggered when the "Análisis" tab is selected.
 */
@Composable
fun FarmerDashboardScreen(
    onAddProductClick: () -> Unit,
    onAnalysisClick: () -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(DashboardTab.PRODUCTOS) }

    val statCards = listOf(
        StatCardData(
            value = "12",
            label = "Productos",
            valueColor = GreenPrimary,
            backgroundColor = GreenLight,
            icon = Icons.Filled.Inventory2
        ),
        StatCardData(
            value = "5",
            label = "Pendientes",
            valueColor = GreenPrimary,
            backgroundColor = Color(0xFFFFFDE7),
            icon = Icons.Filled.PendingActions
        ),
        StatCardData(
            value = "$3.2M",
            label = "Ventas/mes",
            valueColor = GrayDark,
            backgroundColor = GrayLight,
            icon = Icons.Filled.Sell
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            DashboardHeader()

            Spacer(modifier = Modifier.height(20.dp))

            StatCardsRow(cards = statCards)

            Spacer(modifier = Modifier.height(24.dp))

            TabBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    if (tab == DashboardTab.ANALISIS) {
                        onAnalysisClick()
                    } else {
                        selectedTab = tab
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (selectedTab) {
                DashboardTab.PRODUCTOS -> ProductListSection(products = sampleProducts)
                DashboardTab.PEDIDOS   -> RecentSalesSection(sales = sampleRecentSales)
                DashboardTab.ANALISIS  -> { /* handled via callback */ }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AddProductButton(onClick = onAddProductClick)
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun DashboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(GreenLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Agriculture,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(28.dp)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Mi Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                text = "Gestiona tus productos y pedidos",
                style = MaterialTheme.typography.bodyMedium,
                color = GrayMedium
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Stat cards
// ---------------------------------------------------------------------------

@Composable
private fun StatCardsRow(cards: List<StatCardData>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        cards.forEach { card ->
            StatCard(data = card, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(
    data: StatCardData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(data.backgroundColor)
            .padding(vertical = 14.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = data.icon,
            contentDescription = null,
            tint = data.valueColor.copy(alpha = 0.65f),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = data.value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = data.valueColor,
            textAlign = TextAlign.Center
        )
        Text(
            text = data.label,
            style = MaterialTheme.typography.bodySmall,
            color = GrayMedium,
            textAlign = TextAlign.Center
        )
    }
}

// ---------------------------------------------------------------------------
// Tab bar
// ---------------------------------------------------------------------------

@Composable
private fun TabBar(
    selectedTab: DashboardTab,
    onTabSelected: (DashboardTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(GrayLight)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DashboardTab.entries.forEach { tab ->
            val isSelected = tab == selectedTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) White else Color.Transparent)
                    .clickable { onTabSelected(tab) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isSelected) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null,
                            tint = GreenPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) GreenPrimary else GrayMedium
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Product list
// ---------------------------------------------------------------------------

@Composable
private fun ProductListSection(products: List<Product>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Mis Productos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        products.forEach { product ->
            ProductRow(product = product)
        }
    }
}

@Composable
private fun ProductRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GrayLight)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Thumbnail
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(GreenLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Agriculture,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(24.dp)
            )
        }

        // Name + price + stock
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = "\$${formatPrice(product.price)}/${product.unit} · ${formatQuantity(product.available)} ${product.unit}",
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }

        // Status badge
        StatusBadge(status = product.status)
    }
}

@Composable
private fun StatusBadge(status: ProductStatus) {
    val label: String
    val textColor: Color
    val bgColor: Color

    when (status) {
        ProductStatus.ACTIVE -> {
            label = "Activo"
            textColor = GreenPrimary
            bgColor = Color(0xFFDCF5E0)
        }
        ProductStatus.SOLD -> {
            label = "Vendido"
            textColor = Color(0xFF3F51B5)
            bgColor = Color(0xFFE8EAF6)
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

// ---------------------------------------------------------------------------
// Recent sales section (Pedidos tab)
// ---------------------------------------------------------------------------

@Composable
private fun RecentSalesSection(sales: List<RecentSale>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ventas recientes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = "${sales.size} ventas",
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
        }
        sales.forEach { sale ->
            RecentSaleRow(sale = sale)
        }
    }
}

@Composable
private fun RecentSaleRow(sale: RecentSale) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GrayLight)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50))
                .background(GreenPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sale.buyerInitials,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = sale.buyerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
                if (sale.isNew) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(GreenPrimary)
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text("Nuevo", style = MaterialTheme.typography.labelSmall, color = White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Text(
                text = "${sale.productName} · ${sale.quantity} ${sale.unit}",
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium
            )
            Text(
                text = sale.date,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium.copy(alpha = 0.7f)
            )
        }

        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "$${"%,d".format(sale.totalPrice)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Add product CTA
// ---------------------------------------------------------------------------

@Composable
private fun AddProductButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary,
            contentColor = White
        ),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "+ Agregar Producto",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------------------------------------------------------------------------
// Formatting helpers
// ---------------------------------------------------------------------------

private fun formatPrice(price: Int): String =
    if (price >= 1_000) {
        val thousands = price / 1_000
        val remainder = price % 1_000
        if (remainder == 0) "$thousands,000" else "$price"
    } else {
        price.toString()
    }

private fun formatQuantity(qty: Int): String =
    if (qty >= 1_000) "${qty / 1_000},000" else qty.toString()

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true, name = "Farmer Dashboard")
@Composable
private fun FarmerDashboardScreenPreview() {
    AgroConectaTheme {
        FarmerDashboardScreen(
            onAddProductClick = {},
            onAnalysisClick = {}
        )
    }
}
