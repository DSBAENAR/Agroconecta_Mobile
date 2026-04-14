package com.agroconecta.mobile.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.ui.theme.GrayDark
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenDark
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Hardcoded sample data
// ---------------------------------------------------------------------------

private data class ProductDetail(
    val name: String,
    val category: String,
    val rating: String,
    val price: String,
    val unit: String,
    val availableStock: String,
    val minOrder: String,
    val location: String,
    val description: String,
    val farmerInitials: String,
    val farmerName: String,
    val farmerSubtitle: String
)

private val sampleProduct = ProductDetail(
    name = "Tomates Cherry",
    category = "Frutas y Verduras",
    rating = "4.8",
    price = "3.500",
    unit = "kg",
    availableStock = "500 kg",
    minOrder = "10 kg",
    location = "Boyacá",
    description = "Tomate cherry cultivado en las montañas de Boyacá con más de 20 años de " +
        "tradición agrícola. Variedad nacional de excelente sabor dulce, ideal para ensaladas " +
        "y consumo en fresco. Producción sin pesticidas, con riego por goteo y control " +
        "biológico de plagas. Cosecha semanal garantizando frescura.",
    farmerInitials = "JP",
    farmerName = "Juan Pérez",
    farmerSubtitle = "Agricultor verificado · 150 ventas"
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onContactFarmerClick: () -> Unit,
    onViewFarmerClick: () -> Unit
) {
    val product = sampleProduct
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // ----------------------------------------------------------------
            // Back button
            // ----------------------------------------------------------------
            BackButton(onBackClick = onBackClick)

            // ----------------------------------------------------------------
            // Product image placeholder
            // ----------------------------------------------------------------
            ProductImagePlaceholder()

            // ----------------------------------------------------------------
            // Content below the image
            // ----------------------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                // Category pill + Rating pill
                CategoryAndRatingRow(
                    category = product.category,
                    rating = product.rating
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Product name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price + unit
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$ ${product.price}",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenPrimary,
                            fontSize = 36.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "/ ${product.unit}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = GrayMedium,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Description
                DescriptionSection(description = product.description)

                Spacer(modifier = Modifier.height(20.dp))

                // Info cards row
                InfoCardsRow(
                    availableStock = product.availableStock,
                    minOrder = product.minOrder,
                    location = product.location
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Farmer card
                FarmerCard(
                    initials = product.farmerInitials,
                    name = product.farmerName,
                    subtitle = product.farmerSubtitle,
                    onViewFarmerClick = onViewFarmerClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Add to cart button
                Button(
                    onClick = onAddToCartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Agregar al Carrito",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Contact farmer button (outlined)
                OutlinedButton(
                    onClick = onContactFarmerClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.5.dp,
                        color = GreenPrimary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = GreenPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Contactar Agricultor",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Private composables
// ---------------------------------------------------------------------------

@Composable
private fun BackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable(onClick = onBackClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Volver",
            tint = GreenPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Volver",
            style = MaterialTheme.typography.titleMedium.copy(
                color = GreenPrimary,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun ProductImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(GreenLight),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(GreenPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = null,
                tint = GreenPrimary.copy(alpha = 0.55f),
                modifier = Modifier.size(52.dp)
            )
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Descripción",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = GrayDark,
                lineHeight = 22.sp
            )
        )
    }
}

@Composable
private fun CategoryAndRatingRow(category: String, rating: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Category pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(GreenPrimary)
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = White,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // Rating pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(GreenLight)
                .border(
                    width = 1.dp,
                    color = GreenPrimary.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(
                text = "\u2605 $rating",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = GreenDark,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
private fun InfoCardsRow(
    availableStock: String,
    minOrder: String,
    location: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        InfoCard(value = availableStock, label = "Disponible",  modifier = Modifier.weight(1f))
        InfoCard(value = minOrder,       label = "Min. pedido", modifier = Modifier.weight(1f))
        InfoCard(
            value = location,
            label = "Ubicación",
            icon = Icons.Filled.LocationOn,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun InfoCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Black
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = GrayMedium
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FarmerCard(
    initials: String,
    name: String,
    subtitle: String,
    onViewFarmerClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Name and subtitle
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GrayMedium
                    )
                )
            }

            // "Ver" link
            Text(
                text = "Ver",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = GreenPrimary,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable(onClick = onViewFarmerClick)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
        }
    }
}
