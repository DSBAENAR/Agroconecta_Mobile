package com.agroconecta.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
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
import com.agroconecta.mobile.data.model.UserStatus
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenDark
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// SAMPLE DATA (solo para preview)
// ---------------------------------------------------------------------------

private val sampleFarmer = Farmer(
    id = 1,
    name = "Juan Pérez",
    location = "Boyacá, Colombia",
    email = "juan.perez@email.com",
    phone = "+57 310 123 4567",
    status = UserStatus.ACTIVE
)

// ---------------------------------------------------------------------------
// SCREEN
// ---------------------------------------------------------------------------

@Composable
fun ProfileScreen(
    isFarmer: Boolean = false,
    onEditProfileClick: () -> Unit
) {
    if (isFarmer) {
        FarmerProfileContent(farmer = sampleFarmer, onEditProfileClick = onEditProfileClick)
    } else {
        BuyerProfileContent(onEditProfileClick = onEditProfileClick)
    }
}

// ---------------------------------------------------------------------------
// FARMER (FIXED)
// ---------------------------------------------------------------------------

@Composable
private fun FarmerProfileContent(
    farmer: Farmer,
    onEditProfileClick: () -> Unit
) {
    Scaffold(containerColor = White) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileAvatarSimple(name = farmer.name)

            Spacer(modifier = Modifier.height(16.dp))

            ProfileName(name = farmer.name)

            Spacer(modifier = Modifier.height(10.dp))

            RoleBadge(label = "Agricultor")

            Spacer(modifier = Modifier.height(28.dp))

            InfoSection(
                location = farmer.location,
                email = farmer.email,
                phone = farmer.phone
            )

            Spacer(modifier = Modifier.height(32.dp))

            EditProfileButton(onClick = onEditProfileClick)
        }
    }
}

// ---------------------------------------------------------------------------
// BUYER (LO DEJAMOS SIMPLE PARA NO ROMPER)
// ---------------------------------------------------------------------------

@Composable
private fun BuyerProfileContent(onEditProfileClick: () -> Unit) {
    Scaffold(containerColor = White) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileAvatarSimple(name = "Carlos Martínez")

            Spacer(modifier = Modifier.height(16.dp))

            ProfileName(name = "Carlos Martínez")

            Spacer(modifier = Modifier.height(10.dp))

            RoleBadge(label = "Comprador")

            Spacer(modifier = Modifier.height(28.dp))

            InfoSection(
                location = "Bogotá, Colombia",
                email = "carlos.m@email.com",
                phone = "+57 315 987 6543"
            )

            Spacer(modifier = Modifier.height(32.dp))

            EditProfileButton(onClick = onEditProfileClick)
        }
    }
}

// ---------------------------------------------------------------------------
// AVATAR SIMPLE (SIN INITIALS DEPENDENCY)
// ---------------------------------------------------------------------------

@Composable
private fun ProfileAvatarSimple(name: String) {

    val initials = name
        .split(" ")
        .take(2)
        .joinToString("") { it.first().uppercase() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(104.dp)
            .clip(CircleShape)
            .background(GreenPrimary)
    ) {
        Text(
            text = initials,
            color = White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ---------------------------------------------------------------------------
// NAME
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
// ROLE BADGE
// ---------------------------------------------------------------------------

@Composable
private fun RoleBadge(label: String) {
    Box(
        modifier = Modifier
            .border(1.5.dp, GreenPrimary, RoundedCornerShape(50))
            .padding(horizontal = 14.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            color = GreenPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------------------------------------------------------------------------
// INFO SECTION
// ---------------------------------------------------------------------------

@Composable
private fun InfoSection(
    location: String,
    email: String,
    phone: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        SectionHeader("Información")

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(Icons.Filled.LocationOn, location)

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(Icons.Filled.Email, email)

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(Icons.Filled.Phone, phone)
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = Black)
    }
}

// ---------------------------------------------------------------------------
// HEADER
// ---------------------------------------------------------------------------

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = Black,
        fontSize = 18.sp
    )
}

// ---------------------------------------------------------------------------
// BUTTON
// ---------------------------------------------------------------------------

@Composable
private fun EditProfileButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary,
            contentColor = White
        )
    ) {
        Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Editar Perfil")
    }
}

// ---------------------------------------------------------------------------
// PREVIEW
// ---------------------------------------------------------------------------

@Preview(showBackground = true)
@Composable
private fun PreviewFarmer() {
    ProfileScreen(isFarmer = true, onEditProfileClick = {})
}