package com.agroconecta.mobile.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.Black
import com.agroconecta.mobile.ui.theme.GrayLight
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

private data class AppNotification(
    val id: Int,
    val title: String,
    val body: String,
    val time: String,
    val icon: ImageVector,
    val iconTint: Color,
    val iconBg: Color,
    val read: Boolean = false
)

private val mockNotifications = listOf(
    AppNotification(
        id = 1,
        title = "Pago en procesamiento",
        body = "Tu venta de Cacao Premium ($310.000) está siendo procesada.",
        time = "Ahora",
        icon = Icons.Outlined.AttachMoney,
        iconTint = Color(0xFF7C4DFF),
        iconBg = Color(0xFFEDE7F6),
        read = false
    ),
    AppNotification(
        id = 2,
        title = "Nueva venta confirmada",
        body = "Vendiste Tomates Cherry por $350.000. El pago llegará pronto.",
        time = "15 May 2026",
        icon = Icons.Outlined.CheckCircle,
        iconTint = Color(0xFF388E3C),
        iconBg = Color(0xFFE8F5E9),
        read = true
    ),
    AppNotification(
        id = 3,
        title = "Nueva venta confirmada",
        body = "Vendiste Café Pergamino por $520.000.",
        time = "13 May 2026",
        icon = Icons.Outlined.CheckCircle,
        iconTint = Color(0xFF388E3C),
        iconBg = Color(0xFFE8F5E9),
        read = true
    ),
    AppNotification(
        id = 4,
        title = "Nuevo pedido recibido",
        body = "Un comprador ha agregado Maíz Dulce a su carrito.",
        time = "10 May 2026",
        icon = Icons.Outlined.ShoppingCart,
        iconTint = Color(0xFFF57C00),
        iconBg = Color(0xFFFFF3E0),
        read = true
    ),
    AppNotification(
        id = 5,
        title = "Retiro procesado",
        body = "Tu retiro de $850.000 a Bancolombia fue completado.",
        time = "05 May 2026",
        icon = Icons.Outlined.AttachMoney,
        iconTint = Color(0xFF0288D1),
        iconBg = Color(0xFFE1F5FE),
        read = true
    ),
    AppNotification(
        id = 6,
        title = "Consejo de la plataforma",
        body = "Actualiza el stock de tus productos para aparecer mejor en búsquedas.",
        time = "01 May 2026",
        icon = Icons.Outlined.Info,
        iconTint = GrayMedium,
        iconBg = Color(0xFFF5F5F5),
        read = true
    ),
)

@Composable
fun NotificationsScreen(onBackClick: () -> Unit) {
    val unread = mockNotifications.count { !it.read }

    Scaffold(
        containerColor = GrayLight
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // ── Top bar ──────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = GreenPrimary
                    )
                }

                Text(
                    text = "Notificaciones",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    modifier = Modifier.weight(1f)
                )

                if (unread > 0) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .background(GreenPrimary, RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "$unread nuevas",
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // ── List ─────────────────────────────────────────────────────
            if (mockNotifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = GrayMedium,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Sin notificaciones",
                            style = MaterialTheme.typography.bodyLarge,
                            color = GrayMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(mockNotifications, key = { it.id }) { notif ->
                        NotificationItem(notif)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(notif: AppNotification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notif.read) White else Color(0xFFF0FBF0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (notif.read) 1.dp else 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(notif.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notif.icon,
                    contentDescription = null,
                    tint = notif.iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = notif.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (notif.read) FontWeight.Normal else FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (!notif.read) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(GreenPrimary)
                        )
                    }
                }
                Spacer(Modifier.height(3.dp))
                Text(
                    text = notif.body,
                    style = MaterialTheme.typography.bodySmall,
                    color = GrayMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = notif.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = GrayMedium
                )
            }
        }
    }
}
