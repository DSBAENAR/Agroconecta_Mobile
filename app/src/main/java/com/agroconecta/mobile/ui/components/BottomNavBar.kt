package com.agroconecta.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.ui.navigation.Screen
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.White

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val isLogout: Boolean = false
)

@Composable
fun AgroBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {

    val session = SessionManager.session ?: return

    val items = when (session.role.name) {

        "FARMER" -> listOf(
            BottomNavItem(
                "INICIO",
                Icons.Filled.Home,
                Screen.FarmerHome.route
            ),

            BottomNavItem(
                "BUSCAR",
                Icons.Filled.Search,
                Screen.Marketplace.route
            ),

            BottomNavItem(
                "PANEL",
                Icons.Outlined.BarChart,
                Screen.FarmerDashboard.route
            ),

            BottomNavItem(
                "PERFIL",
                Icons.Filled.Person,
                Screen.Profile.route
            ),

            BottomNavItem(
                "SALIR",
                Icons.AutoMirrored.Filled.ExitToApp,
                "logout",
                true
            )
        )

        else -> listOf(

            BottomNavItem(
                "INICIO",
                Icons.Filled.Home,
                Screen.BuyerHome.route
            ),

            BottomNavItem(
                "BUSCAR",
                Icons.Filled.Search,
                Screen.Marketplace.route
            ),

            BottomNavItem(
                "CARRITO",
                Icons.Filled.ShoppingCart,
                Screen.Cart.route
            ),

            BottomNavItem(
                "PANEL",
                Icons.Outlined.BarChart,
                Screen.BuyerDashboard.route
            ),

            BottomNavItem(
                "PERFIL",
                Icons.Filled.Person,
                Screen.Profile.route
            ),

            BottomNavItem(
                "SALIR",
                Icons.AutoMirrored.Filled.ExitToApp,
                "logout",
                true
            )
        )
    }

    BottomNavBarContent(
        items = items,
        currentRoute = normalizeRoute(currentRoute),
        onNavigate = onNavigate,
        onLogout = onLogout
    )
}

@Composable
private fun BottomNavBarContent(
    items: List<BottomNavItem>,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .navigationBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFEEEEEE))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                NavItem(
                    item = item,
                    selected = normalizeRoute(currentRoute) ==
                            normalizeRoute(item.route),

                    onClick = {

                        if (item.isLogout) {
                            onLogout()
                        } else {
                            onNavigate(item.route)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 4.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    if (selected) GreenPrimary
                    else Color.Transparent
                )
                .padding(horizontal = 20.dp, vertical = 6.dp),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (selected) White else GrayMedium,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) GreenPrimary else GrayMedium
        )
    }
}

private fun normalizeRoute(route: String): String {

    return route
        .substringBefore("/")
        .substringBefore("?")
}