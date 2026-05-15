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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.*
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.*

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit,
    onPublishSimilarClick: () -> Unit = {},
    viewModel: ProductViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val isFarmer = SessionManager.session?.role == UserRole.FARMER
    val products = viewModel.products
    val farmer = userViewModel.currentFarmer

    LaunchedEffect(Unit) {
        if (products.isEmpty()) {
            viewModel.loadProducts()
        }
    }

    val product = remember(products, productId) {
        products.find { it.id.toString() == productId }
    }

    LaunchedEffect(product?.farmerId) {
        product?.farmerId?.let {
            userViewModel.loadFarmer(it)
        }
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
                farmerName = farmer?.name ?: "Cargando...",
                isFarmer = isFarmer,
                onBackClick = onBackClick,
                onAddToCart = { cartViewModel.addToCart(product) },
                onPublishSimilarClick = onPublishSimilarClick,
                onContactFarmerClick = onContactFarmerClick,
                onViewFarmerClick = onViewFarmerClick
            )
        }
    }
}

@Composable
private fun ProductDetailContent(
    product: Product,
    farmerName: String,
    isFarmer: Boolean,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit,
    onPublishSimilarClick: () -> Unit,
    onContactFarmerClick: (String) -> Unit,
    onViewFarmerClick: (String) -> Unit
) {

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
                    a = "${product.available} ${product.unit}",
                    b = "${product.minOrder} ${product.unit}",
                    c = product.location
                )

                Spacer(modifier = Modifier.height(24.dp))

                FarmerCard(
                    initials = farmerInitials,
                    name = farmerName,
                    subtitle = "Productor verificado",
                    onViewFarmerClick = {
                        onViewFarmerClick(product.farmerId.toString())
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (isFarmer) {
                    PublishSimilarButton(onPublishSimilarClick)
                } else {
                    AddToCartButton(onAddToCart)
                    Spacer(Modifier.height(12.dp))
                    ContactFarmerButton(product.id.toString(), onContactFarmerClick)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PublishSimilarButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(Icons.Default.Add, null)
        Spacer(Modifier.width(8.dp))
        Text("Publicar producto similar", fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun AddToCartButton(
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary
        )
    ) {

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text("Agregar al carrito")
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

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {

            for (i in 1..maxStars) {

                val starValue = when {
                    i.toFloat() <= animatedRating -> 1f
                    (i.toFloat() - animatedRating) < 1f ->
                        animatedRating - (i - 1).toFloat()

                    else -> 0f
                }

                Star(fill = starValue)
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = String.format("%.1f", rating),
            color = GrayDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun Star(fill: Float) {

    Box(
        modifier = Modifier.size(18.dp)
    ) {

        Icon(
            imageVector = Icons.Default.StarBorder,
            contentDescription = null,
            tint = GrayMedium,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .fillMaxWidth(fill)
        ) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
private fun Pill(
    text: String,
    background: Color,
    textColor: Color
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
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
private fun BackButton(
    onBackClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBackClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
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
                .background(
                    GreenPrimary.copy(alpha = 0.15f)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                tint = GreenPrimary,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
private fun PriceSection(
    price: Double,
    unit: String
) {

    Row(
        verticalAlignment = Alignment.Bottom
    ) {

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
private fun DescriptionSection(
    description: String
) {

    Column {

        Text(
            text = "Descripción",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            color = GrayDark
        )
    }
}

@Composable
private fun InfoCardsRow(
    a: String,
    b: String,
    c: String
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        InfoCard(
            value = a,
            label = "Disponible",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            value = b,
            label = "Min. pedido",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            value = c,
            label = "Ubicación",
            modifier = Modifier.weight(1f),
            icon = Icons.Default.LocationOn
        )
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
        colors = CardDefaults.cardColors(
            containerColor = GrayLight
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            icon?.let {

                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = GreenPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = value,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = label,
                color = GrayMedium
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
        colors = CardDefaults.cardColors(
            containerColor = GrayLight
        ),
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
                    color = White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    color = GrayMedium
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
private fun ContactFarmerButton(
    productId: String,
    onContactFarmerClick: (String) -> Unit
) {

    OutlinedButton(
        onClick = {
            onContactFarmerClick(productId)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(1.5.dp, GreenPrimary)
    ) {

        Icon(
            imageVector = Icons.Outlined.ChatBubbleOutline,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text("Contactar agricultor")
    }
}