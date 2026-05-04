package com.agroconecta.mobile.ui.screens.product

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.*
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.ProductViewModel
import com.agroconecta.mobile.ui.viewmodel.UserViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onAddToCartClick: (String) -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel() // ✅ agregado
) {
    val products = viewModel.products
    val farmer = userViewModel.currentFarmer // ✅ agregado

    LaunchedEffect(Unit) {
        if (products.isEmpty()) {
            viewModel.loadProducts()
        }
    }

    val product = remember(products, productId) {
        products.find { it.id.toString() == productId }
    }

    // ✅ cargar agricultor real
    LaunchedEffect(product?.farmerId) {
        product?.farmerId?.let {
            userViewModel.loadFarmer(it)
        }
    }

    when {
        viewModel.isLoading && product == null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        }

        product == null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Producto no encontrado")
            }
        }

        else -> {
            ProductDetailContent(
                product = product,
                farmerName = farmer?.name ?: "Cargando...", // ✅ agregado
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
    farmerName: String, // ✅ agregado
    onBackClick: () -> Unit,
    onAddToCartClick: (String) -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit
){
    val description = if (product.description.isBlank()) {
        "Producto fresco y de alta calidad, cultivado por agricultores verificados de AgroConecta."
    } else {
        product.description
    }

    val farmerInitials = farmerName
        .split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.toString() }
        .joinToString("")
        .uppercase();

    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            BackButton(onBackClick)
            ProductImagePlaceholder()

            Column(Modifier.fillMaxWidth().padding(16.dp)) {

                CategoryAndRatingRow(
                    category = product.category,
                    rating = product.rating.toDouble()
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )

                Spacer(Modifier.height(12.dp))

                PriceSection(product.price, product.unit)

                Spacer(Modifier.height(24.dp))

                DescriptionSection(description)

                Spacer(Modifier.height(24.dp))

                InfoCardsRow(
                    "${product.available} ${product.unit}",
                    "${product.minOrder} ${product.unit}",
                    product.location
                )

                Spacer(Modifier.height(24.dp))

                FarmerCard(
                    initials = farmerInitials,
                    name = farmerName,
                    subtitle = "Productor verificado",
                    onViewFarmerClick = {
                        onViewFarmerClick(product.farmerId.toString())
                    }
                )

                Spacer(Modifier.height(24.dp))

                AddToCartButton(product.id.toString(), onAddToCartClick)

                Spacer(Modifier.height(12.dp))

                ContactFarmerButton(product.id.toString(), onContactFarmerClick)

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CategoryAndRatingRow(
    category: String,
    rating: Double
) {
    val cappedRating = rating.coerceIn(0.0, 5.0)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Pill(
            text = category,
            background = GreenPrimary,
            textColor = White
        )

        StarRating(rating = cappedRating)
    }
}

@Composable
fun StarRating(
    rating: Double,
    maxStars: Int = 5
) {
    val animatedRating by animateFloatAsState(
        targetValue = rating.toFloat(),
        animationSpec = tween(600),
        label = "ratingAnim"
    )

    Row(verticalAlignment = Alignment.CenterVertically) {

        Row {
            for (i in 1..maxStars) {

                val starValue = when {
                    i.toFloat() <= animatedRating -> 1f
                    (i.toFloat() - animatedRating) < 1f -> animatedRating - (i - 1).toFloat()
                    else -> 0f
                }

                Star(fill = starValue)
            }
        }

        Spacer(Modifier.width(6.dp))

        Text(
            text = String.format("%.1f", rating),
            color = GrayDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun Star(fill: Float) {
    Box(Modifier.size(18.dp)) {

        Icon(
            Icons.Default.StarBorder,
            contentDescription = null,
            tint = GrayMedium,
            modifier = Modifier.matchParentSize()
        )

        Box(
            Modifier
                .matchParentSize()
                .fillMaxWidth(fill)
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

/* =========================
   🔹 TUS COMPONENTES (NO TOCADOS)
   ========================= */

@Composable
private fun Pill(
    text: String,
    background: Color,
    textColor: Color
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = textColor, fontWeight = FontWeight.SemiBold)
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
        Icon(Icons.Default.ArrowBack, null, tint = GreenPrimary)
        Spacer(Modifier.width(8.dp))
        Text("Volver", color = GreenPrimary, fontWeight = FontWeight.SemiBold)
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
            Icon(Icons.Default.Eco, null, tint = GreenPrimary, modifier = Modifier.size(60.dp))
        }
    }
}

@Composable
private fun PriceSection(price: Double, unit: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            "$ ${String.format("%,.0f", price).replace(",", ".")}",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
        Spacer(Modifier.width(8.dp))
        Text("/ $unit", color = GrayMedium, modifier = Modifier.padding(bottom = 6.dp))
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column {
        Text("Descripción", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(description, color = GrayDark)
    }
}

@Composable
private fun InfoCardsRow(a: String, b: String, c: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        InfoCard(a, "Disponible", Modifier.weight(1f))
        InfoCard(b, "Min. pedido", Modifier.weight(1f))
        InfoCard(c, "Ubicación", Modifier.weight(1f), Icons.Default.LocationOn)
    }
}

@Composable
private fun InfoCard(
    value: String,
    label: String,
    modifier: Modifier,
    icon: ImageVector? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let {
                Icon(it, null, tint = GreenPrimary)
                Spacer(Modifier.height(6.dp))
            }

            Text(value, fontWeight = FontWeight.Bold)
            Text(label, color = GrayMedium)
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
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(52.dp).clip(CircleShape).background(GreenPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(initials, color = White)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(subtitle, color = GrayMedium)
            }

            Text(
                "Ver",
                color = GreenPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onViewFarmerClick() }
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
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
    ) {
        Icon(Icons.Default.ShoppingCart, null)
        Spacer(Modifier.width(8.dp))
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
        modifier = Modifier.fillMaxWidth().height(56.dp),
        border = BorderStroke(1.5.dp, GreenPrimary)
    ) {
        Icon(Icons.Outlined.ChatBubbleOutline, null)
        Spacer(Modifier.width(8.dp))
        Text("Contactar agricultor")
    }
}