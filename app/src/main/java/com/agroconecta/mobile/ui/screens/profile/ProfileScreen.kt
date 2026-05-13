package com.agroconecta.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    onEditProfileClick: () -> Unit,
    onAccountClick: () -> Unit = {},
    viewModel: UserViewModel = hiltViewModel()
) {

    val session = SessionManager.session
    val farmer = viewModel.currentFarmer
    val buyer = viewModel.currentBuyer
    val isLoading = viewModel.isLoading

    LaunchedEffect(session?.userId) {
        val id = session?.userId ?: return@LaunchedEffect

        when (session.role) {
            UserRole.FARMER -> viewModel.loadFarmer(id)
            UserRole.BUYER -> viewModel.loadBuyer(id)
            else -> {}
        }
    }

    when (session?.role) {

        UserRole.FARMER -> {
            when {
                isLoading -> LoadingState()
                farmer == null -> ErrorState()
                else -> ProfileContent(
                    name = farmer.name,
                    email = farmer.email,
                    phone = farmer.phone,
                    location = farmer.location,
                    role = "Agricultor",
                    onEditProfileClick = onEditProfileClick,
                    onAccountClick = onAccountClick
                )
            }
        }

        UserRole.BUYER -> {
            when {
                isLoading -> LoadingState()
                buyer == null -> ErrorState()
                else -> ProfileContent(
                    name = buyer.name,
                    email = buyer.email,
                    phone = buyer.phone,
                    location = null,
                    role = "Comprador",
                    onEditProfileClick = onEditProfileClick
                )
            }
        }

        else -> LoadingState()
    }
}

/* ---------------- UI ---------------- */

@Composable
fun ProfileContent(
    name: String,
    email: String,
    phone: String,
    location: String?,
    role: String,
    onEditProfileClick: () -> Unit,
    onAccountClick: (() -> Unit)? = null
) {
    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Avatar(name)

            Spacer(Modifier.height(16.dp))

            Text(name, fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            RoleBadge(role)

            Spacer(Modifier.height(28.dp))

            InfoCard(location, email, phone)

            if (onAccountClick != null) {
                Spacer(Modifier.height(20.dp))
                AccountCard(onClick = onAccountClick)
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = onEditProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary,
                    contentColor = Color.White
                )
            ) {
                Text("Editar perfil")
            }
        }
    }
}

/* ---------------- COMPONENTS ---------------- */

@Composable
private fun Avatar(name: String) {

    val initials = name
        .trim()
        .split(" ")
        .take(2)
        .joinToString("") { it.firstOrNull()?.uppercase() ?: "" }
        .ifEmpty { "U" }

    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(GreenPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(initials, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RoleBadge(role: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(GreenLight)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(role, color = GreenPrimary, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun InfoCard(location: String?, email: String, phone: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            location?.let {
                InfoRow("📍", it)
                Spacer(Modifier.height(12.dp))
            }

            InfoRow("✉️", email)
            Spacer(Modifier.height(12.dp))

            InfoRow("📞", phone)
        }
    }
}

@Composable
private fun InfoRow(icon: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(icon)
        Spacer(Modifier.width(10.dp))
        Text(text)
    }
}

/* ---------------- STATES ---------------- */

@Composable
private fun AccountCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GreenPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mi Cuenta",
                    fontWeight = FontWeight.SemiBold,
                    color = GreenPrimary
                )
                Text(
                    text = "Saldo, historial y datos bancarios",
                    style = MaterialTheme.typography.bodySmall,
                    color = GrayMedium
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = GreenPrimary
            )
        }
    }
}

@Composable
fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = GreenPrimary)
    }
}

@Composable
fun ErrorState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No se pudo cargar el perfil")
    }
}