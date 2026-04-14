package com.agroconecta.mobile.ui.screens.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.model.Product
import com.agroconecta.mobile.data.model.ProductStatus
import com.agroconecta.mobile.ui.theme.AgroConectaTheme
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Sample data
// ---------------------------------------------------------------------------

private val sampleProducts = listOf(
    Product(
        id = "1",
        name = "Tomates Cherry",
        category = "Verduras",
        price = 3500,
        unit = "kg",
        available = 200,
        minOrder = 5,
        location = "Boyacá",
        rating = 4.8f,
        imageUrl = "",
        farmerId = "f1",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "2",
        name = "Lechuga Orgánica",
        category = "Verduras",
        price = 2800,
        unit = "kg",
        available = 150,
        minOrder = 3,
        location = "Cundinamarca",
        rating = 4.6f,
        imageUrl = "",
        farmerId = "f2",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "3",
        name = "Mango Tommy",
        category = "Frutas",
        price = 4200,
        unit = "kg",
        available = 300,
        minOrder = 10,
        location = "Tolima",
        rating = 4.9f,
        imageUrl = "",
        farmerId = "f3",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "4",
        name = "Maíz Amarillo",
        category = "Cereales",
        price = 1800,
        unit = "kg",
        available = 500,
        minOrder = 20,
        location = "Córdoba",
        rating = 4.5f,
        imageUrl = "",
        farmerId = "f4",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "5",
        name = "Fresas Frescas",
        category = "Frutas",
        price = 6500,
        unit = "kg",
        available = 80,
        minOrder = 2,
        location = "Cundinamarca",
        rating = 4.7f,
        imageUrl = "",
        farmerId = "f5",
        status = ProductStatus.ACTIVE
    ),
    Product(
        id = "6",
        name = "Arroz Integral",
        category = "Cereales",
        price = 2200,
        unit = "kg",
        available = 1000,
        minOrder = 25,
        location = "Huila",
        rating = 4.4f,
        imageUrl = "",
        farmerId = "f6",
        status = ProductStatus.ACTIVE
    )
)

private val categories = listOf("Todos", "Frutas", "Verduras", "Cereales")
private val locations  = listOf("Todos", "Zipaquirá", "Chía", "Cajicá", "Tabio", "Facatativá", "Boyacá", "Cundinamarca", "Tolima", "Huila", "Córdoba")

// Distinct muted background colors used as image placeholders per product index
private val placeholderColors = listOf(
    Color(0xFFFFCDD2),
    Color(0xFFC8E6C9),
    Color(0xFFBBDEFB),
    Color(0xFFFFF9C4),
    Color(0xFFE1BEE7),
    Color(0xFFFFE0B2)
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    onProductClick: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var selectedLocationIndex by remember { mutableIntStateOf(0) }

    // Pending state inside bottom sheet before applying
    var pendingCategoryIndex by remember { mutableIntStateOf(0) }
    var pendingLocationIndex by remember { mutableIntStateOf(0) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val filteredProducts = remember(searchQuery, selectedCategoryIndex, selectedLocationIndex) {
        var result = sampleProducts
        if (selectedCategoryIndex != 0) {
            result = result.filter { it.category == categories[selectedCategoryIndex] }
        }
        if (selectedLocationIndex != 0) {
            val loc = locations[selectedLocationIndex]
            result = result.filter { it.location.contains(loc, ignoreCase = true) }
        }
        if (searchQuery.isNotBlank()) {
            result = result.filter { it.name.contains(searchQuery.trim(), ignoreCase = true) }
        }
        result
    }

    val activeFilters = (if (selectedCategoryIndex != 0) 1 else 0) + (if (selectedLocationIndex != 0) 1 else 0)

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = White
        ) {
            FilterBottomSheetContent(
                pendingCategoryIndex = pendingCategoryIndex,
                pendingLocationIndex = pendingLocationIndex,
                onCategorySelected = { pendingCategoryIndex = it },
                onLocationSelected = { pendingLocationIndex = it },
                onApply = {
                    selectedCategoryIndex = pendingCategoryIndex
                    selectedLocationIndex = pendingLocationIndex
                    scope.launch { sheetState.hide() }.invokeOnCompletion { showFilterSheet = false }
                },
                onReset = {
                    pendingCategoryIndex = 0
                    pendingLocationIndex = 0
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Marketplace",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Black
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = {
                            pendingCategoryIndex = selectedCategoryIndex
                            pendingLocationIndex = selectedLocationIndex
                            showFilterSheet = true
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Tune,
                                contentDescription = "Filtros",
                                tint = if (activeFilters > 0) GreenPrimary else Black,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        // Badge showing active filter count
                        if (activeFilters > 0) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(GreenPrimary)
                                    .align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = activeFilters.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = White,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        containerColor = GrayLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // White header section: search bar + category chips
            Surface(color = White, shadowElevation = 2.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)
                ) {
                    SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

                    Spacer(modifier = Modifier.height(12.dp))

                    CategoryChipRow(
                        categories = categories,
                        selectedIndex = selectedCategoryIndex,
                        onCategorySelected = { selectedCategoryIndex = it }
                    )

                    // Active location chip badge when filter is applied
                    if (selectedLocationIndex != 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.LocationOn, null, tint = GreenPrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(GreenPrimary)
                                    .clickable {
                                        selectedLocationIndex = 0
                                        pendingLocationIndex = 0
                                    }
                                    .padding(horizontal = 12.dp, vertical = 5.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = locations[selectedLocationIndex],
                                        style = MaterialTheme.typography.labelLarge,
                                        color = White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("✕", style = MaterialTheme.typography.labelSmall, color = White)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Product count label
            Text(
                text = "${filteredProducts.size} productos encontrados",
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Product grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = filteredProducts,
                    key = { it.id }
                ) { product ->
                    val colorIndex = sampleProducts.indexOf(product).coerceIn(0, placeholderColors.lastIndex)
                    ProductCard(
                        product = product,
                        placeholderColor = placeholderColors[colorIndex],
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Filter bottom sheet
// ---------------------------------------------------------------------------

@Composable
private fun FilterBottomSheetContent(
    pendingCategoryIndex: Int,
    pendingLocationIndex: Int,
    onCategorySelected: (Int) -> Unit,
    onLocationSelected: (Int) -> Unit,
    onApply: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp)
    ) {
        // Handle + title
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(GrayBorder)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Filtros", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Black)
            Text(
                text = "Restablecer",
                style = MaterialTheme.typography.labelLarge,
                color = GreenPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onReset() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Category filter
        Text("Categoría", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Black)
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(categories) { index, label ->
                CategoryChip(label = label, selected = index == pendingCategoryIndex, onClick = { onCategorySelected(index) })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Location filter
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.LocationOn, null, tint = GreenPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Zona / Municipio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Black)
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Grid of location chips (wrap)
        val rows = locations.chunked(3)
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                row.forEach { loc ->
                    val idx = locations.indexOf(loc)
                    CategoryChip(label = loc, selected = idx == pendingLocationIndex, onClick = { onLocationSelected(idx) })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Apply button
        Button(
            onClick = onApply,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary, contentColor = White)
        ) {
            Text("Aplicar filtros", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}

// ---------------------------------------------------------------------------
// Search bar
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Buscar productos...",
                style = MaterialTheme.typography.bodyMedium,
                color = GrayMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar",
                tint = GrayMedium,
                modifier = Modifier.size(20.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Black),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = GrayLight,
            focusedBorderColor = GreenPrimary,
            unfocusedBorderColor = GrayBorder,
            cursorColor = GreenPrimary
        )
    )
}

// ---------------------------------------------------------------------------
// Category chip row
// ---------------------------------------------------------------------------

@Composable
private fun CategoryChipRow(
    categories: List<String>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(categories) { index, label ->
            CategoryChip(
                label = label,
                selected = index == selectedIndex,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) GreenPrimary else White
    val textColor = if (selected) White else GrayMedium
    val borderColor = if (selected) GreenPrimary else GrayBorder

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 7.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ---------------------------------------------------------------------------
// Product card
// ---------------------------------------------------------------------------

@Composable
private fun ProductCard(
    product: Product,
    placeholderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(placeholderColor),
                contentAlignment = Alignment.Center
            ) {
                // Decorative inner circle to add visual depth to the placeholder
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(placeholderColor.copy(alpha = 0.5f))
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Product name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Location
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Ubicación",
                        tint = GrayMedium,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = product.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Price
                Text(
                    text = "$${"%,d".format(product.price)}/${product.unit}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    color = GreenPrimary
                )

                // Star rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Calificación",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Black
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MarketplaceScreenPreview() {
    AgroConectaTheme {
        MarketplaceScreen(
            onProductClick = {},
            onFilterClick = {}
        )
    }
}
