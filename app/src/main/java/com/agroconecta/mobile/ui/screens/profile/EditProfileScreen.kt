package com.agroconecta.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agroconecta.mobile.data.model.Buyer
import com.agroconecta.mobile.data.model.Farmer
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White
import com.agroconecta.mobile.ui.viewmodel.UserViewModel

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    val session = viewModel.session
    val farmer = viewModel.currentFarmer
    val buyer = viewModel.currentBuyer

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    LaunchedEffect(farmer, buyer) {

        farmer?.let {
            name = it.name
            email = it.email
            phone = it.phone
            location = it.location
        }

        buyer?.let {
            name = it.name
            email = it.email
            phone = it.phone
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            BackHeader(onBackClick)

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ProfileImage()
            }

            Spacer(modifier = Modifier.height(32.dp))

            AgroTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nombre"
            )

            Spacer(modifier = Modifier.height(16.dp))

            AgroTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo"
            )

            Spacer(modifier = Modifier.height(16.dp))

            AgroTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Teléfono"
            )

            if (session?.role == UserRole.FARMER) {

                Spacer(modifier = Modifier.height(16.dp))

                AgroTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = "Ubicación"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {

                    when (session?.role) {

                        UserRole.FARMER -> {

                            farmer?.let {

                                viewModel.updateFarmer(
                                    it.copy(
                                        name = name,
                                        email = email,
                                        phone = phone,
                                        location = location
                                    )
                                )
                            }
                        }

                        UserRole.BUYER -> {

                            buyer?.let {

                                viewModel.updateBuyer(
                                    it.copy(
                                        name = name,
                                        email = email,
                                        phone = phone
                                    )
                                )
                            }
                        }

                        else -> {}
                    }

                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenPrimary
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Guardar cambios",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BackHeader(
    onBackClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = GreenPrimary,
            modifier = Modifier
                .size(26.dp)
                .clickable {
                    onBackClick()
                }
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
            .background(GreenPrimary.copy(alpha = 0.15f)),
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

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            focusedLabelColor = GreenPrimary,
            cursorColor = GreenPrimary
        )
    )
}