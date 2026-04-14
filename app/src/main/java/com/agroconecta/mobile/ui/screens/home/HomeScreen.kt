package com.agroconecta.mobile.ui.screens.home

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Nature
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.AgroConectaTheme
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Internal data models
// ---------------------------------------------------------------------------

private data class HomeProduct(
    val name: String,
    val location: String,
    val price: String,
    val unit: String,
    val rating: Float,
    val sales: String,
    val bgColor: Color,
    val icon: ImageVector,
    val iconTint: Color
)

private data class HomeCategory(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val pillBg: Color,
    val products: List<HomeProduct>
)

// ---------------------------------------------------------------------------
// Data
// ---------------------------------------------------------------------------

private val top10 = listOf(
    HomeProduct("Tomates Cherry",   "Boyacá",       "$3,500","kg", 4.8f,"350 ventas", Color(0xFFFFEBEE), Icons.Filled.LocalFlorist,  Color(0xFFE53935)),
    HomeProduct("Fresas Frescas",   "Cundinamarca", "$6,500","kg", 4.9f,"280 ventas", Color(0xFFFCE4EC), Icons.Outlined.LocalFlorist, Color(0xFFD81B60)),
    HomeProduct("Papas Criollas",   "Nariño",       "$3,200","kg", 4.6f,"260 ventas", Color(0xFFFFF8E1), Icons.Filled.Nature,         Color(0xFF6D4C41)),
    HomeProduct("Aguacate Hass",    "Antioquia",    "$5,800","kg", 4.8f,"240 ventas", Color(0xFFE8F5E9), Icons.Filled.Eco,            Color(0xFF2E7D32)),
    HomeProduct("Mango Tommy",      "Tolima",       "$4,200","kg", 4.7f,"220 ventas", Color(0xFFFFF3E0), Icons.Filled.Park,           Color(0xFFF57F17)),
    HomeProduct("Zanahoria",        "Cundinamarca", "$2,100","kg", 4.5f,"200 ventas", Color(0xFFFFF3E0), Icons.Outlined.Eco,          Color(0xFFE65100)),
    HomeProduct("Lechuga Orgánica", "Cundinamarca", "$2,800","kg", 4.5f,"190 ventas", Color(0xFFF1F8E9), Icons.Filled.Grass,          Color(0xFF388E3C)),
    HomeProduct("Maíz Amarillo",    "Córdoba",      "$1,800","kg", 4.4f,"175 ventas", Color(0xFFFFFDE7), Icons.Filled.Agriculture,    Color(0xFFF9A825)),
    HomeProduct("Pimentón Rojo",    "Boyacá",       "$4,500","kg", 4.6f,"160 ventas", Color(0xFFFFEBEE), Icons.Outlined.LocalFlorist, Color(0xFFC62828)),
    HomeProduct("Cebolla Cabezona", "Boyacá",       "$3,000","kg", 4.3f,"155 ventas", Color(0xFFF3E5F5), Icons.Filled.Spa,            Color(0xFF7B1FA2)),
)

private val categories = listOf(
    HomeCategory(
        title = "Frutas",
        icon = Icons.Filled.LocalFlorist, iconTint = Color(0xFFD81B60), pillBg = Color(0xFFFCE4EC),
        products = listOf(
            HomeProduct("Banano Bocadillo","Santander",   "$3,800","kg", 4.7f,"130 ventas", Color(0xFFFFFDE7), Icons.Outlined.Park,          Color(0xFFF9A825)),
            HomeProduct("Piña Gold",       "Meta",        "$4,500","kg", 4.6f,"120 ventas", Color(0xFFFFF8E1), Icons.Filled.Park,             Color(0xFFFB8C00)),
            HomeProduct("Mandarina",       "Caldas",      "$3,200","kg", 4.8f,"110 ventas", Color(0xFFFFF3E0), Icons.Outlined.LocalFlorist,   Color(0xFFE65100)),
            HomeProduct("Uva Isabella",    "Valle",       "$7,200","kg", 4.5f,"95 ventas",  Color(0xFFF3E5F5), Icons.Filled.Spa,              Color(0xFF7B1FA2)),
        )
    ),
    HomeCategory(
        title = "Verduras",
        icon = Icons.Filled.Eco, iconTint = Color(0xFF2E7D32), pillBg = Color(0xFFE8F5E9),
        products = listOf(
            HomeProduct("Brócoli Fresco",  "Cundinamarca","$4,200","kg", 4.7f,"115 ventas", Color(0xFFE8F5E9), Icons.Filled.Eco,             Color(0xFF2E7D32)),
            HomeProduct("Espinaca Baby",   "Boyacá",      "$5,500","kg", 4.8f,"98 ventas",  Color(0xFFF1F8E9), Icons.Filled.Grass,            Color(0xFF388E3C)),
            HomeProduct("Pepino Cohombro", "Tolima",      "$2,400","kg", 4.4f,"88 ventas",  Color(0xFFE0F2F1), Icons.Outlined.Eco,            Color(0xFF00796B)),
            HomeProduct("Remolacha",       "Nariño",      "$2,900","kg", 4.5f,"82 ventas",  Color(0xFFEDE7F6), Icons.Outlined.LocalFlorist,   Color(0xFF6A1B9A)),
        )
    ),
    HomeCategory(
        title = "Plantas Naturales",
        icon = Icons.Filled.Yard, iconTint = Color(0xFF1B5E20), pillBg = Color(0xFFDCEDC8),
        products = listOf(
            HomeProduct("Hierbabuena",     "Cundinamarca","$8,000","bnd",4.9f,"75 ventas",  Color(0xFFE8F5E9), Icons.Filled.Spa,              Color(0xFF2E7D32)),
            HomeProduct("Albahaca Fresca", "Antioquia",   "$9,500","bnd",4.8f,"68 ventas",  Color(0xFFF1F8E9), Icons.Outlined.Spa,            Color(0xFF388E3C)),
            HomeProduct("Cilantro",        "Boyacá",      "$5,000","bnd",4.6f,"62 ventas",  Color(0xFFE8F5E9), Icons.Filled.Grass,            Color(0xFF1B5E20)),
            HomeProduct("Sábila",          "Atlántico",   "$6,000","und",4.7f,"55 ventas",  Color(0xFFE0F2F1), Icons.Outlined.Nature,         Color(0xFF00695C)),
        )
    ),
    HomeCategory(
        title = "Semillas",
        icon = Icons.Filled.Nature, iconTint = Color(0xFF33691E), pillBg = Color(0xFFF9FBE7),
        products = listOf(
            HomeProduct("Semilla de Maíz", "Córdoba",     "$12,000","kg",4.7f,"50 ventas",  Color(0xFFFFFDE7), Icons.Filled.Agriculture,      Color(0xFFF9A825)),
            HomeProduct("Fríjol Cargam.",  "Boyacá",      "$9,800","kg", 4.6f,"44 ventas",  Color(0xFFFFEBEE), Icons.Outlined.Nature,         Color(0xFFBF360C)),
            HomeProduct("Quinua Orgánica", "Boyacá",      "$18,000","kg",4.9f,"40 ventas",  Color(0xFFF9FBE7), Icons.Filled.Nature,           Color(0xFF558B2F)),
            HomeProduct("Arveja Seca",     "Nariño",      "$7,500","kg", 4.5f,"38 ventas",  Color(0xFFE8F5E9), Icons.Outlined.Eco,            Color(0xFF2E7D32)),
        )
    ),
    HomeCategory(
        title = "Cereales y Granos",
        icon = Icons.Filled.Agriculture, iconTint = Color(0xFFF57F17), pillBg = Color(0xFFFFF8E1),
        products = listOf(
            HomeProduct("Arroz Integral",  "Huila",       "$2,200","kg", 4.4f,"145 ventas", Color(0xFFFFFDE7), Icons.Outlined.Agriculture,    Color(0xFFF57F17)),
            HomeProduct("Cebada",          "Boyacá",      "$3,100","kg", 4.3f,"88 ventas",  Color(0xFFFFF8E1), Icons.Filled.Park,             Color(0xFF8D6E63)),
            HomeProduct("Trigo Nacional",  "Nariño",      "$2,800","kg", 4.5f,"76 ventas",  Color(0xFFFFF3E0), Icons.Outlined.Park,           Color(0xFFBF360C)),
            HomeProduct("Sorgo",           "Córdoba",     "$1,900","kg", 4.2f,"60 ventas",  Color(0xFFFFF8E1), Icons.Filled.Grass,            Color(0xFF827717)),
        )
    ),
)

// ---------------------------------------------------------------------------
// Screen root
// ---------------------------------------------------------------------------

@Composable
fun HomeScreen(
    onExploreClick: () -> Unit,
    onFarmerClick: () -> Unit,
    onProductClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        SectionHeader(title = "Top 10 Más Vendidos", onVerTodos = onExploreClick)
        Spacer(modifier = Modifier.height(12.dp))
        Top10Grid(products = top10, onProductClick = onProductClick)

        Spacer(modifier = Modifier.height(28.dp))

        categories.forEach { category ->
            CategorySection(category = category, onVerTodos = onExploreClick, onProductClick = onProductClick)
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Section header
// ---------------------------------------------------------------------------

@Composable
private fun SectionHeader(title: String, onVerTodos: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        Text(
            text = "Ver todos →",
            style = MaterialTheme.typography.labelLarge,
            color = GreenPrimary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { onVerTodos() }
        )
    }
}

// ---------------------------------------------------------------------------
// Top 10 — 2-column grid
// ---------------------------------------------------------------------------

@Composable
private fun Top10Grid(products: List<HomeProduct>, onProductClick: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        products.chunked(2).forEachIndexed { rowIndex, rowProducts ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowProducts.forEachIndexed { colIndex, product ->
                    TopProductCard(
                        product = product,
                        rank = rowIndex * 2 + colIndex + 1,
                        modifier = Modifier.weight(1f),
                        onClick = { onProductClick(product.name) }
                    )
                }
                if (rowProducts.size < 2) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopProductCard(
    product: HomeProduct,
    rank: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Image area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(product.bgColor)
            ) {
                // Rank badge
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (rank <= 3) Color(0xFFFFB300) else Color(0xFF9E9E9E))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "#$rank",
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Product icon
                Icon(
                    imageVector = product.icon,
                    contentDescription = product.name,
                    tint = product.iconTint,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Black, maxLines = 1)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, tint = GrayMedium, modifier = Modifier.size(11.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(product.location, style = MaterialTheme.typography.bodySmall, color = GrayMedium, maxLines = 1)
                }
                Text("${product.price}/${product.unit}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(product.rating.toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = Black)
                    }
                    Text(product.sales, style = MaterialTheme.typography.bodySmall, color = GrayMedium)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Category section — horizontal scroll
// ---------------------------------------------------------------------------

@Composable
private fun CategorySection(
    category: HomeCategory,
    onVerTodos: () -> Unit,
    onProductClick: (String) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(category.pillBg)
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = null,
                            tint = category.iconTint,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }
                }
            }
            Text(
                text = "Ver todos →",
                style = MaterialTheme.typography.labelLarge,
                color = GreenPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onVerTodos() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(category.products) { product ->
                CategoryProductCard(
                    product = product,
                    onClick = { onProductClick(product.name) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryProductCard(product: HomeProduct, onClick: () -> Unit = {}) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(145.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(product.bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = product.icon,
                    contentDescription = product.name,
                    tint = product.iconTint,
                    modifier = Modifier.size(38.dp)
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Black, maxLines = 1)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, tint = GrayMedium, modifier = Modifier.size(11.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(product.location, style = MaterialTheme.typography.bodySmall, color = GrayMedium, maxLines = 1)
                }
                Text("${product.price}/${product.unit}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GreenPrimary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(product.rating.toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = Black)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AgroConectaTheme {
        HomeScreen(onExploreClick = {}, onFarmerClick = {})
    }
}
