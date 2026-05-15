package com.agroconecta.mobile.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.ProductViewModel

@Composable
fun FarmerHomeScreen(
    onPublishClick: () -> Unit,
    onProductClick: (String) -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products = viewModel.products
    val isLoading = viewModel.isLoading
    val farmerName = SessionManager.session?.name?.split(" ")?.firstOrNull() ?: "Agricultor"

    val trending = remember(products) {
        products.sortedByDescending { it.rating }.take(6)
    }

    val categories = remember(products) {
        products.map { it.category }.distinct().sorted()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        if (isLoading && products.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = GreenPrimary
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 20.dp)
            ) {
                // ── Greeting ──────────────────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Hola, $farmerName 👋",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "¿Qué quieres vender hoy?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = GrayMedium
                        )
                    }

                    IconButton(onClick = onNotificationsClick) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificaciones",
                            tint = GreenPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Publish CTA ───────────────────────────────────────────
                Button(
                    onClick = onPublishClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Publicar Producto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Trending ──────────────────────────────────────────────
                FarmerSectionHeader(
                    title = "Tendencias del Mercado",
                    icon = Icons.Default.TrendingUp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (trending.isEmpty()) {
                    FarmerEmptySection()
                } else {
                    TrendingProductRow(products = trending, onProductClick = onProductClick)
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── By category ───────────────────────────────────────────
                categories.forEach { category ->
                    val categoryProducts = products.filter {
                        it.category.equals(category, ignoreCase = true)
                    }

                    if (categoryProducts.isNotEmpty()) {
                        FarmerSectionHeader(title = "Lo que se vende: $category")
                        Spacer(modifier = Modifier.height(12.dp))
                        TrendingProductRow(
                            products = categoryProducts,
                            onProductClick = onProductClick
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FarmerSectionHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

@Composable
private fun FarmerEmptySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay datos de mercado disponibles",
            style = MaterialTheme.typography.bodyMedium,
            color = GrayMedium
        )
    }
}

@Composable
private fun TrendingProductRow(
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            TrendingProductCard(
                product = product,
                onClick = { onProductClick(product.id.toString()) }
            )
        }
    }
}

@Composable
private fun TrendingProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = product.name,
                    tint = GreenPrimary,
                    modifier = Modifier.size(50.dp)
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GrayMedium,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = product.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${"%,.0f".format(product.price)}/${product.unit}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating pill
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "⭐ ${product.rating}",
                        style = MaterialTheme.typography.labelSmall,
                        color = GreenPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
