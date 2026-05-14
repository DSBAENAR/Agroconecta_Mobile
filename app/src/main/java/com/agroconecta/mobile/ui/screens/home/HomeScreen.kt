package com.agroconecta.mobile.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
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
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.CartViewModel
import com.agroconecta.mobile.ui.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    onExploreClick: () -> Unit,
    onFarmerClick: () -> Unit,
    onProductClick: (String) -> Unit = {},
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val products = viewModel.products
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        if (products.isEmpty()) {
            viewModel.loadProducts()
        }
    }

    val topProducts = remember(products) {
        products.sortedByDescending { it.rating }.take(5)
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

                SectionHeader(
                    title = "Top Productos",
                    onVerTodos = onExploreClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (topProducts.isEmpty()) {

                    EmptySection()

                } else {

                    ProductRow(
                        products = topProducts,
                        onProductClick = onProductClick,
                        onAddToCart = {
                            cartViewModel.addToCart(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                categories.forEach { category ->

                    val categoryProducts = products.filter {
                        it.category.equals(category, ignoreCase = true)
                    }

                    if (categoryProducts.isNotEmpty()) {

                        CategorySection(
                            category = category,
                            products = categoryProducts,
                            onVerTodos = onExploreClick,
                            onProductClick = onProductClick,
                            onAddToCart = {
                                cartViewModel.addToCart(it)
                            }
                        )

                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptySection() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "No hay productos disponibles",
            style = MaterialTheme.typography.bodyMedium,
            color = GrayMedium
        )
    }
}

@Composable
private fun CategorySection(
    category: String,
    products: List<Product>,
    onVerTodos: () -> Unit,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit
) {

    Column {

        SectionHeader(
            title = category,
            onVerTodos = onVerTodos
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProductRow(
            products = products,
            onProductClick = onProductClick,
            onAddToCart = onAddToCart
        )
    }
}

@Composable
private fun ProductRow(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit
) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        items(
            items = products,
            key = { it.id }
        ) { product ->

            ProductCard(
                product = product,
                onClick = {
                    onProductClick(product.id.toString())
                },
                onAddToCart = {
                    onAddToCart(product)
                }
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onVerTodos: () -> Unit
) {

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

        TextButton(onClick = onVerTodos) {

            Text(
                text = "Ver todos",
                color = GreenPrimary
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {

    Card(
        modifier = Modifier.width(190.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(0xFFE8F5E9))
                    .clickable {
                        onClick()
                    },

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = product.name,
                    tint = GreenPrimary,
                    modifier = Modifier.size(58.dp)
                )
            }

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GrayMedium,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = product.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$${"%,.0f".format(product.price)}/${product.unit}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onAddToCart,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Agregar")
                }
            }
        }
    }
}