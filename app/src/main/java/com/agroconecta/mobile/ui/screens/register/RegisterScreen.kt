package com.agroconecta.mobile.ui.screens.register

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.OrangeAccent
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.White
import com.agroconecta.mobile.ui.theme.Black

// ---------------------------------------------------------------------------
// Password rules
// ---------------------------------------------------------------------------

private data class PasswordRule(
    val label: String,
    val check: (String) -> Boolean
)

private val passwordRules = listOf(
    PasswordRule("Mínimo 8 caracteres")          { it.length >= 8 },
    PasswordRule("Al menos una mayúscula")        { it.any { c -> c.isUpperCase() } },
    PasswordRule("Al menos una minúscula")        { it.any { c -> c.isLowerCase() } },
    PasswordRule("Al menos un número")            { it.any { c -> c.isDigit() } },
    PasswordRule("Al menos un carácter especial") { it.any { c -> !c.isLetterOrDigit() } }
)

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@Composable
fun RegisterScreen(
    isFarmer: Boolean = false,
    onRegisterClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmVisible by rememberSaveable { mutableStateOf(false) }
    var passwordFocused by rememberSaveable { mutableStateOf(false) }

    val accentColor = if (isFarmer) OrangeAccent else GreenPrimary
    val roleLabel = if (isFarmer) "Agricultor / Vendedor" else "Comprador"

    val passedRules = passwordRules.count { it.check(password) }
    val strength = if (password.isEmpty()) 0f else passedRules / passwordRules.size.toFloat()

    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = roleLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = accentColor,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Name
            FieldLabel("Nombre completo")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Juan Pérez", color = GrayMedium) },
                leadingIcon = {
                    Icon(Icons.Filled.Person, null, tint = GrayMedium, modifier = Modifier.size(20.dp))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(accentColor)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone
            FieldLabel("Teléfono")
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("+57 300 000 0000", color = GrayMedium) },
                leadingIcon = {
                    Icon(Icons.Filled.Phone, null, tint = GrayMedium, modifier = Modifier.size(20.dp))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(accentColor)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            FieldLabel("Correo electrónico")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("tu@email.com", color = GrayMedium) },
                leadingIcon = {
                    Icon(Icons.Filled.Email, null, tint = GrayMedium, modifier = Modifier.size(20.dp))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(accentColor)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            FieldLabel("Contraseña")
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordFocused = true
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("••••••••", color = GrayMedium) },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, null, tint = GrayMedium, modifier = Modifier.size(20.dp))
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null,
                            tint = GrayMedium,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(accentColor)
            )

            // Password strength + checklist — shown as soon as the user starts typing
            if (passwordFocused || password.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                PasswordStrengthCard(
                    password = password,
                    strength = strength,
                    accentColor = accentColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            FieldLabel("Confirmar contraseña")
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("••••••••", color = GrayMedium) },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, null, tint = GrayMedium, modifier = Modifier.size(20.dp))
                },
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(
                            imageVector = if (confirmVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null,
                            tint = GrayMedium,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                shape = RoundedCornerShape(14.dp),
                colors = fieldColors(accentColor)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = White)
            ) {
                Text("Crear cuenta", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(GrayBorder))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = GrayMedium)) { append("¿Ya tienes cuenta? ") }
                    withStyle(SpanStyle(color = accentColor, fontWeight = FontWeight.Bold)) { append("Inicia sesión") }
                },
                modifier = Modifier.clickable { onLoginClick() },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Password strength card
// ---------------------------------------------------------------------------

@Composable
private fun PasswordStrengthCard(
    password: String,
    strength: Float,
    accentColor: Color
) {
    val strengthColor by animateColorAsState(
        targetValue = when {
            strength < 0.4f -> Color(0xFFE53935)   // red
            strength < 0.8f -> OrangeAccent          // orange
            else            -> GreenPrimary          // green
        },
        animationSpec = tween(300),
        label = "strengthColor"
    )
    val strengthLabel = when {
        password.isEmpty() -> ""
        strength < 0.4f    -> "Débil"
        strength < 0.8f    -> "Media"
        else               -> "Fuerte"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Strength bar + label
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Seguridad de la contraseña",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
                if (strengthLabel.isNotEmpty()) {
                    Text(
                        text = strengthLabel,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = strengthColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = strength,
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                color = strengthColor,
                trackColor = GrayBorder,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Checklist
            passwordRules.forEach { rule ->
                val passed = rule.check(password)
                PasswordRuleRow(label = rule.label, passed = passed)
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
private fun PasswordRuleRow(label: String, passed: Boolean) {
    val iconColor by animateColorAsState(
        targetValue = if (passed) GreenPrimary else GrayMedium,
        animationSpec = tween(250),
        label = "ruleColor"
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(if (passed) GreenLight else GrayLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (passed) Icons.Filled.Check else Icons.Filled.Close,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(11.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (passed) Black else GrayMedium
        )
    }
}

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = Black,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
    )
}

@Composable
private fun fieldColors(accentColor: Color) =
    OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = GrayLight,
        focusedContainerColor = White,
        unfocusedBorderColor = GrayBorder,
        focusedBorderColor = accentColor,
        cursorColor = accentColor
    )
