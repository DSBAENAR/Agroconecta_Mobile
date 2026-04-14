package com.agroconecta.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenDark
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Sample data
// ---------------------------------------------------------------------------

private val sampleFarmer = Farmer(
    id = "farmer-001",
    name = "Juan Pérez",
    initials = "JP",
    isVerified = true,
    totalSales = 150,
    totalProducts = 12,
    rating = 4.8f,
    clients = 45,
    location = "Boyacá, Colombia",
    email = "juan.perez@email.com",
    phone = "+57 310 123 4567",
    certifications = listOf("Orgánico", "Comercio Justo", "BPA")
)

private data class BuyerProfile(
    val name: String,
    val initials: String,
    val location: String,
    val email: String,
    val phone: String,
    val totalPurchases: Int,
    val pendingOrders: Int,
    val totalSpent: String,
    val favoriteProducts: Int
)

private val sampleBuyer = BuyerProfile(
    name = "Carlos Martínez",
    initials = "CM",
    location = "Bogotá, Colombia",
    email = "carlos.m@email.com",
    phone = "+57 315 987 6543",
    totalPurchases = 8,
    pendingOrders = 3,
    totalSpent = "$1.2M",
    favoriteProducts = 12
)

// ---------------------------------------------------------------------------
// Screen entry point
// ---------------------------------------------------------------------------

@Composable
fun ProfileScreen(
    isFarmer: Boolean = false,
    onEditProfileClick: () -> Unit
) {
    if (isFarmer) {
        FarmerProfileContent(farmer = sampleFarmer, onEditProfileClick = onEditProfileClick)
    } else {
        BuyerProfileContent(buyer = sampleBuyer, onEditProfileClick = onEditProfileClick)
    }
}

@Composable
private fun FarmerProfileContent(farmer: Farmer, onEditProfileClick: () -> Unit) {
    Scaffold(containerColor = White) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileAvatar(initials = farmer.initials, isFarmer = true)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileName(name = farmer.name)
            Spacer(modifier = Modifier.height(10.dp))
            RoleBadge(label = "Agricultor Verificado")
            Spacer(modifier = Modifier.height(24.dp))
            StatsRow(
                stats = listOf(
                    farmer.totalProducts.toString() to "Productos",
                    farmer.totalSales.toString()    to "Ventas",
                    String.format("%.1f", farmer.rating) to "Rating",
                    farmer.clients.toString()        to "Clientes"
                ),
                highlightIndex = 2
            )
            Spacer(modifier = Modifier.height(28.dp))
            InfoSection(location = farmer.location, email = farmer.email, phone = farmer.phone)
            Spacer(modifier = Modifier.height(28.dp))
            CertificationsSection(certifications = farmer.certifications)
            Spacer(modifier = Modifier.height(32.dp))
            EditProfileButton(onClick = onEditProfileClick)
        }
    }
}

@Composable
private fun BuyerProfileContent(buyer: BuyerProfile, onEditProfileClick: () -> Unit) {
    Scaffold(containerColor = White) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileAvatar(initials = buyer.initials, isFarmer = false)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileName(name = buyer.name)
            Spacer(modifier = Modifier.height(10.dp))
            RoleBadge(label = "Comprador")
            Spacer(modifier = Modifier.height(24.dp))
            StatsRow(
                stats = listOf(
                    buyer.totalPurchases.toString()  to "Compras",
                    buyer.pendingOrders.toString()   to "Pendientes",
                    buyer.totalSpent                 to "Gastado",
                    buyer.favoriteProducts.toString() to "Favoritos"
                ),
                highlightIndex = -1
            )
            Spacer(modifier = Modifier.height(28.dp))
            InfoSection(location = buyer.location, email = buyer.email, phone = buyer.phone)
            Spacer(modifier = Modifier.height(32.dp))
            EditProfileButton(onClick = onEditProfileClick)
        }
    }
}

// ---------------------------------------------------------------------------
// Avatar
// ---------------------------------------------------------------------------

@Composable
private fun ProfileAvatar(initials: String, isFarmer: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(104.dp)
            .clip(CircleShape)
            .background(if (isFarmer) GreenPrimary else androidx.compose.ui.graphics.Color(0xFF1565C0))
    ) {
        Text(
            text = initials,
            color = White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

// ---------------------------------------------------------------------------
// Name
// ---------------------------------------------------------------------------

@Composable
private fun ProfileName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        color = Black,
        textAlign = TextAlign.Center
    )
}

// ---------------------------------------------------------------------------
// Role badge
// ---------------------------------------------------------------------------

@Composable
private fun RoleBadge(label: String) {
    Box(
        modifier = Modifier
            .border(width = 1.5.dp, color = GreenPrimary, shape = RoundedCornerShape(50))
            .padding(horizontal = 14.dp, vertical = 5.dp)
    ) {
        Text(text = label, color = GreenPrimary, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
    }
}

// ---------------------------------------------------------------------------
// Stats row
// ---------------------------------------------------------------------------

@Composable
private fun StatsRow(
    stats: List<Pair<String, String>>,
    highlightIndex: Int = -1
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stats.forEachIndexed { index, (value, label) ->
            StatCard(
                value = value,
                label = label,
                valueColor = if (index == highlightIndex) GreenPrimary else null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    valueColor: androidx.compose.ui.graphics.Color?,
    modifier: Modifier = Modifier
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
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor ?: Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = GrayMedium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Info section
// ---------------------------------------------------------------------------

@Composable
private fun InfoSection(
    location: String,
    email: String,
    phone: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        SectionHeader(title = "Información")

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(
            icon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Ubicación",
                    tint = GreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            text = location
        )

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Correo electrónico",
                    tint = GreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            text = email
        )

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Teléfono",
                    tint = GreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            },
            text = phone
        )
    }
}

@Composable
private fun InfoRow(
    icon: @Composable () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        icon()
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Black
        )
    }
}

// ---------------------------------------------------------------------------
// Certifications section
// ---------------------------------------------------------------------------

@Composable
private fun CertificationsSection(certifications: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(title = "Certificaciones")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            certifications.forEach { cert ->
                CertificationBadge(
                    label = cert,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CertificationBadge(
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = GreenLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.WorkspacePremium,
                contentDescription = label,
                tint = GreenDark,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = GreenDark,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Shared section header
// ---------------------------------------------------------------------------

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = Black,
        fontWeight = FontWeight.Bold
    )
}

// ---------------------------------------------------------------------------
// Edit profile button
// ---------------------------------------------------------------------------

@Composable
private fun EditProfileButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary,
            contentColor = White
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Editar Perfil",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Buyer Profile")
@Composable
private fun BuyerProfilePreview() {
    com.agroconecta.mobile.ui.theme.AgroConectaTheme {
        ProfileScreen(isFarmer = false, onEditProfileClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Farmer Profile")
@Composable
private fun FarmerProfilePreview() {
    com.agroconecta.mobile.ui.theme.AgroConectaTheme {
        ProfileScreen(isFarmer = true, onEditProfileClick = {})
    }
}
