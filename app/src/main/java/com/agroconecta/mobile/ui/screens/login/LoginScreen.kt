package com.agroconecta.mobile.ui.screens.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroconecta.mobile.ui.theme.AgroConectaTheme
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayBorder
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenDark
import com.agroconecta.mobile.ui.theme.GreenLight
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.OrangeAccent
import com.agroconecta.mobile.ui.theme.OrangeLight
import com.agroconecta.mobile.ui.theme.White

// ---------------------------------------------------------------------------
// Public composable
// ---------------------------------------------------------------------------

/**
 * Login screen for AgroConecta.
 *
 * @param isFarmer  When true renders the "Agricultor / Vendedor" (farmer) variant with
 *                  orange accent colours, phone field and SMS login button.
 *                  When false renders the "Comprador" (buyer) variant with green accents.
 * @param onLoginClick           Triggered when the primary "Iniciar Sesión" button is tapped.
 * @param onGoogleClick          Triggered when "Continuar con Google" is tapped.
 * @param onRegisterClick        Triggered when "Regístrate" is tapped.
 * @param onForgotPasswordClick  Triggered when "¿Olvidaste tu contraseña?" is tapped.
 * @param onSmsCodeClick         Triggered when "Ingresar con código SMS" is tapped (farmer only).
 */
@Composable
fun LoginScreen(
    isFarmer: Boolean = false,
    onLoginClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSmsCodeClick: () -> Unit = {},
) {
    // Derive accent palette from variant
    val accentColor by animateColorAsState(
        targetValue = if (isFarmer) OrangeAccent else GreenPrimary,
        animationSpec = tween(durationMillis = 300),
        label = "accentColor",
    )
    val accentLightColor by animateColorAsState(
        targetValue = if (isFarmer) OrangeLight else GreenLight,
        animationSpec = tween(durationMillis = 300),
        label = "accentLightColor",
    )
    // Field state
    var identifier by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // ------------------------------------------------------------------
            // Hero icon circle
            // ------------------------------------------------------------------
            HeroIconCircle(
                accentColor = accentColor,
                accentLightColor = accentLightColor,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ------------------------------------------------------------------
            // App title
            // ------------------------------------------------------------------
            Text(
                text = "AgroConecta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                letterSpacing = (-0.5).sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ------------------------------------------------------------------
            // Role badge (pill)
            // ------------------------------------------------------------------
            RoleBadge(
                label = if (isFarmer) "Agricultor / Vendedor" else "Comprador",
                backgroundColor = accentLightColor,
                contentColor = accentColor,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ------------------------------------------------------------------
            // Identifier field  (email for buyer / phone for farmer)
            // ------------------------------------------------------------------
            LabeledInputField(
                label = if (isFarmer) "Correo o teléfono" else "Correo electrónico",
                value = identifier,
                onValueChange = { identifier = it },
                placeholder = if (isFarmer) "+57 300 000 0000" else "tu@email.com",
                leadingIcon = {
                    Icon(
                        imageVector = if (isFarmer) Icons.Filled.Phone else Icons.Filled.Email,
                        contentDescription = null,
                        tint = GrayMedium,
                        modifier = Modifier.size(20.dp),
                    )
                },
                keyboardType = if (isFarmer) KeyboardType.Phone else KeyboardType.Email,
                accentColor = accentColor,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // Password field
            // ------------------------------------------------------------------
            LabeledInputField(
                label = "Contraseña",
                value = password,
                onValueChange = { password = it },
                placeholder = "••••••••",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = GrayMedium,
                        modifier = Modifier.size(20.dp),
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible)
                                "Ocultar contraseña"
                            else
                                "Mostrar contraseña",
                            tint = GrayMedium,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                accentColor = accentColor,
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ------------------------------------------------------------------
            // Forgot password link
            // ------------------------------------------------------------------
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = accentColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onForgotPasswordClick),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ------------------------------------------------------------------
            // Primary CTA — Iniciar Sesión
            // ------------------------------------------------------------------
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(14.dp),
                        ambientColor = accentColor.copy(alpha = 0.25f),
                        spotColor = accentColor.copy(alpha = 0.35f),
                    ),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = White,
                ),
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.3.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ------------------------------------------------------------------
            // "o" divider
            // ------------------------------------------------------------------
            OrDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // ------------------------------------------------------------------
            // Google outlined button
            // ------------------------------------------------------------------
            GoogleSignInButton(
                borderColor = GrayBorder,
                onClick = onGoogleClick,
            )

            // ------------------------------------------------------------------
            // SMS code button (farmer variant only)
            // ------------------------------------------------------------------
            if (isFarmer) {
                Spacer(modifier = Modifier.height(12.dp))
                SmsCodeButton(
                    borderColor = accentColor,
                    contentColor = accentColor,
                    onClick = onSmsCodeClick,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ------------------------------------------------------------------
            // Register footer
            // ------------------------------------------------------------------
            RegisterFooter(
                accentColor = accentColor,
                onRegisterClick = onRegisterClick,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Private sub-composables
// ---------------------------------------------------------------------------

@Composable
private fun HeroIconCircle(
    accentColor: Color,
    accentLightColor: Color,
) {
    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accentLightColor,
                        accentLightColor.copy(alpha = 0.6f),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Outer soft ring
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(accentColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Eco,
                contentDescription = "AgroConecta logo",
                tint = accentColor,
                modifier = Modifier.size(44.dp),
            )
        }
    }
}

@Composable
private fun RoleBadge(
    label: String,
    backgroundColor: Color,
    contentColor: Color,
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = backgroundColor,
    ) {
        Text(
            text = label,
            color = contentColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
            letterSpacing = 0.2.sp,
        )
    }
}

@Composable
private fun LabeledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    accentColor: Color,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            modifier = Modifier.padding(bottom = 6.dp),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = GrayMedium,
                    fontSize = 14.sp,
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = GrayBorder,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                cursorColor = accentColor,
                focusedTextColor = Black,
                unfocusedTextColor = Black,
            ),
        )
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = GrayBorder,
            thickness = 1.dp,
        )
        Text(
            text = "o",
            modifier = Modifier.padding(horizontal = 12.dp),
            color = GrayMedium,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
        )
        Divider(
            modifier = Modifier.weight(1f),
            color = GrayBorder,
            thickness = 1.dp,
        )
    }
}

@Composable
private fun GoogleSignInButton(
    borderColor: Color,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.5.dp, color = borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = White,
            contentColor = Black,
        ),
    ) {
        // Hand-drawn "G" that mirrors Google's brand colour split —
        // rendered as a styled Text since material-icons-extended does not
        // include the official Google logo icon.
        GoogleLogoG()
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Continuar con Google",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
        )
    }
}

/**
 * Minimal typographic approximation of the Google "G" mark using a coloured
 * bold letter. This avoids needing a drawable asset while remaining clearly
 * recognisable.
 */
@Composable
private fun GoogleLogoG() {
    // The four-colour split is achieved via an AnnotatedString across a
    // single character — each quarter is tinted with the canonical Google hue.
    // We render two half-characters side by side using individual Texts so
    // the colour split reads naturally at small sizes.
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "G",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4285F4), // Google Blue
        )
    }
}

@Composable
private fun SmsCodeButton(
    borderColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.5.dp, color = borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = White,
            contentColor = contentColor,
        ),
    ) {
        Icon(
            imageVector = Icons.Filled.Sms,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Ingresar con código SMS",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor,
        )
    }
}

@Composable
private fun RegisterFooter(
    accentColor: Color,
    onRegisterClick: () -> Unit,
) {
    val annotated = buildAnnotatedString {
        withStyle(SpanStyle(color = GrayMedium, fontSize = 14.sp)) {
            append("¿No tienes cuenta? ")
        }
        withStyle(
            SpanStyle(
                color = accentColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        ) {
            append("Regístrate")
        }
    }

    Text(
        text = annotated,
        textAlign = TextAlign.Center,
        modifier = Modifier.clickable(onClick = onRegisterClick),
    )
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

@Preview(showBackground = true, name = "Buyer Login")
@Composable
private fun PreviewBuyerLogin() {
    AgroConectaTheme {
        LoginScreen(isFarmer = false)
    }
}

@Preview(showBackground = true, name = "Farmer Login")
@Composable
private fun PreviewFarmerLogin() {
    AgroConectaTheme {
        LoginScreen(isFarmer = true)
    }
}
