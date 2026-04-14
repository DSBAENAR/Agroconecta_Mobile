package com.agroconecta.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.agroconecta.mobile.ui.theme.GreenPrimary
import com.agroconecta.mobile.ui.theme.GrayMedium
import com.agroconecta.mobile.ui.theme.White

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BuyerBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("INICIO", Icons.Filled.Home, "buyer_home"),
        BottomNavItem("BUSCAR", Icons.Filled.Search, "marketplace"),
        BottomNavItem("PANEL", Icons.Outlined.BarChart, "buyer_dashboard"),
        BottomNavItem("PERFIL", Icons.Filled.Person, "profile")
    )
    BottomNavBarContent(items = items, currentRoute = currentRoute, onNavigate = onNavigate)
}

@Composable
fun FarmerBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("INICIO", Icons.Filled.Home, "farmer_home"),
        BottomNavItem("BUSCAR", Icons.Filled.Search, "marketplace"),
        BottomNavItem("PANEL", Icons.Outlined.BarChart, "farmer_dashboard"),
        BottomNavItem("PERFIL", Icons.Filled.Person, "profile")
    )
    BottomNavBarContent(items = items, currentRoute = currentRoute, onNavigate = onNavigate)
}

@Composable
private fun BottomNavBarContent(
    items: List<BottomNavItem>,
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .navigationBarsPadding()
    ) {
        // Top border
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
                val selected = currentRoute == item.route
                NavItem(
                    item = item,
                    selected = selected,
                    onClick = { onNavigate(item.route) }
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
        // Icon pill — filled green when selected, transparent when not
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(if (selected) GreenPrimary else Color.Transparent)
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
