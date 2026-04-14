package com.agroconecta.mobile.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.AgroConectaTheme
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.GrayDark
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Constants
// ---------------------------------------------------------------------------

private val productCategories = listOf(
    "Frutas", "Verduras", "Tubérculos", "Cereales",
    "Legumbres", "Hortalizas", "Lácteos", "Carnes", "Otro"
)

private val weightUnits = listOf("kg", "lb", "arroba", "bulto", "unidad")

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@Composable
fun CreatePublicationScreen(
    onBackClick: () -> Unit,
    onPublishClick: () -> Unit
) {
    var productName    by remember { mutableStateOf("") }
    var selectedCat    by remember { mutableStateOf(productCategories[0]) }
    var description    by remember { mutableStateOf("") }
    var price          by remember { mutableStateOf("") }
    var selectedUnit   by remember { mutableStateOf("kg") }
    var availableQty   by remember { mutableStateOf("") }
    var minOrder       by remember { mutableStateOf("") }
    var location       by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header ──────────────────────────────────────────────────
            PublicationHeader(onBackClick = onBackClick)

            // ── Scrollable form ──────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {

                // Photo picker
                PhotoPickerBox()

                Spacer(Modifier.height(24.dp))

                // ── Sección: Información ─────────────────────────────────
                SectionHeader("Información del producto")
                Spacer(Modifier.height(12.dp))

                FieldLabel("Nombre del producto")
                Spacer(Modifier.height(6.dp))
                AgroTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    placeholder = "Ej: Tomates Cherry"
                )

                Spacer(Modifier.height(16.dp))

                FieldLabel("Categoría")
                Spacer(Modifier.height(8.dp))
                CategoryChipRow(
                    categories = productCategories,
                    selected = selectedCat,
                    onSelect = { selectedCat = it }
                )

                Spacer(Modifier.height(16.dp))

                FieldLabel("Descripción del producto")
                Spacer(Modifier.height(6.dp))
                AgroTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Describe la variedad, características, método de cultivo, origen...",
                    minLines = 3,
                    maxLines = 6
                )

                Spacer(Modifier.height(24.dp))

                // ── Sección: Precio ──────────────────────────────────────
                SectionHeader("Precio")
                Spacer(Modifier.height(12.dp))

                FieldLabel("Precio por unidad")
                Spacer(Modifier.height(8.dp))
                PriceAndUnitSection(
                    price = price,
                    onPriceChange = { price = it },
                    units = weightUnits,
                    selectedUnit = selectedUnit,
                    onUnitSelect = { selectedUnit = it }
                )

                Spacer(Modifier.height(24.dp))

                // ── Sección: Inventario y logística ──────────────────────
                SectionHeader("Inventario y logística")
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        FieldLabel("Cant. disponible")
                        Spacer(Modifier.height(6.dp))
                        AgroTextField(
                            value = availableQty,
                            onValueChange = { availableQty = it },
                            placeholder = "500",
                            keyboardType = KeyboardType.Number,
                            suffix = selectedUnit
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        FieldLabel("Pedido mínimo")
                        Spacer(Modifier.height(6.dp))
                        AgroTextField(
                            value = minOrder,
                            onValueChange = { minOrder = it },
                            placeholder = "10",
                            keyboardType = KeyboardType.Number,
                            suffix = selectedUnit
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                FieldLabel("Ubicación del producto")
                Spacer(Modifier.height(6.dp))
                AgroTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "Ej: Boyacá, Cundinamarca",
                    leadingIcon = Icons.Filled.LocationOn
                )

                Spacer(Modifier.height(28.dp))

                // ── Botón publicar ───────────────────────────────────────
                Button(
                    onClick = onPublishClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Publicar Producto",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun PublicationHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GreenLight)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Nueva Publicación",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            )
            Text(
                text = "Completa los datos del producto",
                style = MaterialTheme.typography.bodySmall.copy(color = GrayMedium)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Photo picker placeholder
// ---------------------------------------------------------------------------

@Composable
private fun PhotoPickerBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GreenLight)
            .border(
                width = 2.dp,
                color = GreenPrimary.copy(alpha = 0.35f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { /* TODO: abrir selector de imagen */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(GreenPrimary.copy(alpha = 0.13f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AddAPhoto,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = "Agregar foto del producto",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = GreenPrimary
                )
            )
            Text(
                text = "Toca para seleccionar · JPG o PNG",
                style = MaterialTheme.typography.bodySmall.copy(color = GrayMedium)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Section header + field label
// ---------------------------------------------------------------------------

@Composable
private fun SectionHeader(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(4.dp, 18.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(GreenPrimary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Black
            )
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = Black
        )
    )
}

// ---------------------------------------------------------------------------
// Text field
// ---------------------------------------------------------------------------

@Composable
private fun AgroTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String? = null,
    leadingIcon: ImageVector? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(color = GrayMedium)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            unfocusedBorderColor = GrayBorder,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            cursorColor = GreenPrimary
        ),
        minLines = minLines,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = if (suffix != null) {
            {
                Text(
                    text = suffix,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GrayMedium,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        } else null,
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = GrayMedium,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else null
    )
}

// ---------------------------------------------------------------------------
// Category chips
// ---------------------------------------------------------------------------

@Composable
private fun CategoryChipRow(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isSelected) GreenPrimary else GrayLight)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) GreenPrimary else GrayBorder,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable { onSelect(category) }
                    .padding(horizontal = 16.dp, vertical = 9.dp)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) White else GrayDark
                    )
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Price + unit selector
// ---------------------------------------------------------------------------

@Composable
private fun PriceAndUnitSection(
    price: String,
    onPriceChange: (String) -> Unit,
    units: List<String>,
    selectedUnit: String,
    onUnitSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = price,
            onValueChange = onPriceChange,
            placeholder = {
                Text(
                    text = "3.500",
                    style = MaterialTheme.typography.bodyMedium.copy(color = GrayMedium)
                )
            },
            prefix = {
                Text(
                    text = "$ ",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                )
            },
            suffix = {
                Text(
                    text = "/ $selectedUnit",
                    style = MaterialTheme.typography.bodyMedium.copy(color = GrayMedium)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                unfocusedBorderColor = GrayBorder,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                cursorColor = GreenPrimary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            units.forEach { unit ->
                val isSelected = unit == selectedUnit
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) GreenPrimary else GrayLight)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) GreenPrimary else GrayBorder,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onUnitSelect(unit) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) White else GrayDark
                        )
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true, name = "Create Publication")
@Composable
private fun CreatePublicationScreenPreview() {
    AgroConectaTheme {
        CreatePublicationScreen(
            onBackClick = {},
            onPublishClick = {}
        )
    }
}
