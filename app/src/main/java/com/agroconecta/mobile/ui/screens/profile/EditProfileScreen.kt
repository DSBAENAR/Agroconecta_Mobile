package com.agroconecta.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.ui.theme.*
import com.agroconecta.mobile.ui.viewmodel.UserViewModel

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    val session = SessionManager.session

    val farmer = viewModel.currentFarmer
    val buyer = viewModel.currentBuyer

    val isLoading = viewModel.isLoading

    LaunchedEffect(session?.userId) {

        val id = session?.userId ?: return@LaunchedEffect

        when (session.role) {

            UserRole.FARMER -> {
                viewModel.loadFarmer(id)
            }

            UserRole.BUYER -> {
                viewModel.loadBuyer(id)
            }

            else -> {}
        }
    }

    when (session?.role) {

        UserRole.FARMER -> {

            when {

                isLoading -> {
                    ProfileLoadingState()
                }

                farmer == null -> {
                    ProfileErrorState()
                }

                else -> {

                    EditProfileContent(
                        currentName = farmer.name,
                        currentEmail = farmer.email,
                        currentPhone = farmer.phone,
                        onBackClick = onBackClick
                    )
                }
            }
        }

        UserRole.BUYER -> {

            when {

                isLoading -> {
                    ProfileLoadingState()
                }

                buyer == null -> {
                    ProfileErrorState()
                }

                else -> {

                    EditProfileContent(
                        currentName = buyer.name,
                        currentEmail = buyer.email,
                        currentPhone = buyer.phone,
                        onBackClick = onBackClick
                    )
                }
            }
        }

        else -> {
            ProfileLoadingState()
        }
    }
}

@Composable
private fun EditProfileContent(
    currentName: String,
    currentEmail: String,
    currentPhone: String,
    onBackClick: () -> Unit
) {

    var name by remember(currentName) {
        mutableStateOf(currentName)
    }

    var email by remember(currentEmail) {
        mutableStateOf(currentEmail)
    }

    var phone by remember(currentPhone) {
        mutableStateOf(currentPhone)
    }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BackButton(onBackClick)

            Spacer(modifier = Modifier.height(20.dp))

            ProfileImage()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Editar perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )

            Spacer(modifier = Modifier.height(28.dp))

            AgroTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = "Nombre"
            )

            Spacer(modifier = Modifier.height(18.dp))

            AgroTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = "Correo electrónico"
            )

            Spacer(modifier = Modifier.height(18.dp))

            AgroTextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                label = "Teléfono"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {

                    /*
                    Aquí luego haces:

                    viewModel.updateProfile(
                        name,
                        email,
                        phone
                    )
                    */

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                ),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Guardar cambios",
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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
            },

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = GreenPrimary,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Volver",
            color = GreenPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ProfileImage() {

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
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = GreenPrimary,
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
private fun AgroTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    var hasBeenFocused by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->

                if (
                    focusState.isFocused &&
                    !hasBeenFocused
                ) {

                    hasBeenFocused = true

                    onValueChange("")
                }
            },

        singleLine = true,

        shape = RoundedCornerShape(16.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            focusedLabelColor = GreenPrimary,
            cursorColor = GreenPrimary
        )
    )
}

@Composable
private fun ProfileLoadingState() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = GreenPrimary
        )
    }
}

@Composable
private fun ProfileErrorState() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "No se pudo cargar el perfil"
        )
    }
}