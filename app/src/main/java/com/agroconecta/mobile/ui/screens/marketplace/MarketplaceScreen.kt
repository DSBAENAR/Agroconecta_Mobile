package com.agroconecta.mobile.ui.screens.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.data.mock.DataProducts
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.ui.theme.*

private val categories = listOf(
    "Todos",
    "Frutas",
    "Verduras",
    "Cereales"
)

private val placeholderColors = listOf(
    Color(0xFFFFCDD2),
    Color(0xFFC8E6C9),
    Color(0xFFBBDEFB),
    Color(0xFFFFF9C4),
    Color(0xFFD1C4E9),
    Color(0xFFFFE0B2)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    onProductClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableIntStateOf(0) }

    val filteredProducts = remember(searchQuery, selectedCategory) {
        DataProducts.getAllProducts().filter { product ->
            val matchesCategory =
                selectedCategory == 0 ||
                        product.category.equals(
                            categories[selectedCategory],
                            ignoreCase = true
                        )

            val matchesSearch =
                searchQuery.isBlank() ||
                        product.name.contains(
                            searchQuery,
                            ignoreCase = true
                        )

            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Marketplace",
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                },
                actions = {
                    IconButton(onClick = onFilterClick) {
                        Icon(
                            imageVector = Icons.Outlined.Tune,
                            contentDescription = "Filtros"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        },
        containerColor = GrayLight
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Surface(
                color = White,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Buscar productos...")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        },
                        singleLine = true,
                        shape = MaterialTheme.shapes.large
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(categories) { index, category ->
                            FilterChip(
                                selected = selectedCategory == index,
                                onClick = {
                                    selectedCategory = index
                                },
                                label = {
                                    Text(category)
                                }
                            )
                        }
                    }
                }
            }

            Text(
                text = "${filteredProducts.size} productos encontrados",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = GrayMedium
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts, key = { it.id }) { product ->
                    val index = filteredProducts.indexOf(product)

                    MarketplaceProductCard(
                        product = product,
                        placeholderColor = placeholderColors[
                            index % placeholderColors.size
                        ],
                        onClick = {
                            onProductClick(product.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceProductCard(
    product: Product,
    placeholderColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(placeholderColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = product.name,
                    tint = GreenPrimary,
                    modifier = Modifier.size(56.dp)
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

                Spacer(modifier = Modifier.height(6.dp))

                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GrayMedium,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

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

                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketplacePreview() {
    AgroConectaTheme {
        MarketplaceScreen()
    }
}