package com.agroconecta.mobile.ui.screens.product

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onAddToCartClick: (String) -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products = viewModel.products

    LaunchedEffect(Unit) {
        if (products.isEmpty()) {
            viewModel.loadProducts()
        }
    }

    val product = remember(products, productId) {
        products.find { it.id.toString() == productId }
    }

    when {
        viewModel.isLoading && product == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        }

        product == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado")
            }
        }

        else -> {
            ProductDetailContent(
                product = product,
                onBackClick = onBackClick,
                onAddToCartClick = onAddToCartClick,
                onContactFarmerClick = onContactFarmerClick,
                onViewFarmerClick = onViewFarmerClick
            )
        }
    }
}

@Composable
private fun ProductDetailContent(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCartClick: (String) -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit
) {
    val description = if (product.description.isBlank()) {
        "Producto fresco y de alta calidad, cultivado por agricultores verificados de AgroConecta."
    } else {
        product.description
    }

    val farmerInitials = product.name
        .split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.toString() }
        .joinToString("")
        .uppercase()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BackButton(onBackClick)
            ProductImagePlaceholder()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CategoryAndRatingRow(
                    category = product.category,
                    rating = product.rating.toDouble()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                PriceSection(
                    price = product.price,
                    unit = product.unit
                )

                Spacer(modifier = Modifier.height(24.dp))

                DescriptionSection(description)

                Spacer(modifier = Modifier.height(24.dp))

                InfoCardsRow(
                    availableStock = "${product.available} ${product.unit}",
                    minOrder = "${product.minOrder} ${product.unit}",
                    location = product.location
                )

                Spacer(modifier = Modifier.height(24.dp))

                FarmerCard(
                    initials = farmerInitials,
                    name = "Agricultor AgroConecta",
                    subtitle = "Productor verificado",
                    onViewFarmerClick = {
                        onViewFarmerClick(product.farmerId.toString())
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AddToCartButton(
                    productId = product.id.toString(),
                    onAddToCartClick = onAddToCartClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                ContactFarmerButton(
                    productId = product.id.toString(),
                    onContactFarmerClick = onContactFarmerClick
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun BackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onBackClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Volver",
            tint = GreenPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Volver",
            color = GreenPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ProductImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(GreenLight),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(GreenPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Eco,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
private fun CategoryAndRatingRow(
    category: String,
    rating: Double
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Pill(
            text = category,
            background = GreenPrimary,
            textColor = White
        )

        Pill(
            text = "★ $rating",
            background = GreenLight,
            textColor = GreenDark,
            bordered = true
        )
    }
}

@Composable
private fun Pill(
    text: String,
    background: Color,
    textColor: Color,
    bordered: Boolean = false
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .then(
                if (bordered) {
                    Modifier.border(
                        1.dp,
                        GreenPrimary.copy(alpha = 0.25f),
                        RoundedCornerShape(50)
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PriceSection(
    price: Double,
    unit: String
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "$ ${String.format("%,.0f", price).replace(",", ".")}",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "/ $unit",
            color = GrayMedium,
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column {
        Text(
            text = "Descripción",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            color = GrayDark,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun InfoCardsRow(
    availableStock: String,
    minOrder: String,
    location: String
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        InfoCard(
            value = availableStock,
            label = "Disponible",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            value = minOrder,
            label = "Min. pedido",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            value = location,
            label = "Ubicación",
            icon = Icons.Default.LocationOn,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun InfoCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = value,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                textAlign = TextAlign.Center,
                color = GrayMedium,
                style = MaterialTheme.typography.bodySmall
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
        colors = CardDefaults.cardColors(containerColor = GrayLight),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    color = GrayMedium,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = "Ver",
                color = GreenPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onViewFarmerClick()
                }
            )
        }
    }
}

@Composable
private fun AddToCartButton(
    productId: String,
    onAddToCartClick: (String) -> Unit
) {
    Button(
        onClick = { onAddToCartClick(productId) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(Icons.Default.ShoppingCart, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Agregar al carrito")
    }
}

@Composable
private fun ContactFarmerButton(
    productId: String,
    onContactFarmerClick: (String) -> Unit
) {
    OutlinedButton(
        onClick = { onContactFarmerClick(productId) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(1.5.dp, GreenPrimary),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GreenPrimary
        )
    ) {
        Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Contactar agricultor")
    }
}