package com.agroconecta.mobile.ui.screens.product

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.UserViewModel
import com.agroconecta.mobile.ui.viewmodel.ProductViewModel

@Composable
fun FarmerDetailScreen(
    farmerId: String,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: UserViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel()
) {

    val farmer = viewModel.currentFarmer
    val products = productViewModel.products

    LaunchedEffect(farmerId) {
        viewModel.loadFarmer(farmerId.toInt())

        if (products.isEmpty()) {
            productViewModel.loadProducts()
        }
    }

    if (farmer == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GreenPrimary)
        }
    } else {

        val farmerProducts = remember(products, farmerId) {
            products.filter { it.farmerId.toString() == farmerId }
        }

        FarmerDetailContent(
            farmer = farmer,
            products = farmerProducts,
            onBackClick = onBackClick,
            onProductClick = onProductClick
        )
    }
}

@Composable
private fun FarmerDetailContent(
    farmer: Farmer,
    products: List<Product>,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit
) {

    val initials = farmer.name
        .split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.toString() }
        .joinToString("")
        .uppercase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // 🔙 Back
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onBackClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Volver", fontWeight = FontWeight.SemiBold)
        }

        // 👤 HEADER PRO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(GreenPrimary),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = GreenPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = farmer.name,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(farmer.location, color = White, fontSize = 13.sp)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // ⭐ Rating Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("4.5", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text("Productor verificado", color = GrayMedium)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Productos del agricultor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            if (products.isEmpty()) {
                Text(
                    text = "Este agricultor no tiene productos aún",
                    color = GrayMedium
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    products.forEach { product ->
                        ProductItemCard(
                            product = product,
                            onClick = { onProductClick(product.id.toString()) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductItemCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GreenPrimary.copy(alpha = 0.15f))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${product.available} ${product.unit} disponibles",
                    color = GrayMedium,
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "$ ${String.format("%,.0f", product.price).replace(",", ".")}",
                    color = GreenPrimary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Ver",
                    color = GreenPrimary,
                    fontSize = 12.sp
                )
            }
        }
    }
}