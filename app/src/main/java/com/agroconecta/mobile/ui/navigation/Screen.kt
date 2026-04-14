package com.agroconecta.mobile.ui.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object RoleSelection : Screen("role_selection")
    data object LoginBuyer : Screen("login_buyer")
    data object LoginFarmer : Screen("login_farmer")
    data object RegisterBuyer : Screen("register_buyer")
    data object RegisterFarmer : Screen("register_farmer")
    data object BuyerHome : Screen("buyer_home")
    data object FarmerHome : Screen("farmer_home")
    data object Marketplace : Screen("marketplace")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    data object BuyerDashboard : Screen("buyer_dashboard")
    data object FarmerDashboard : Screen("farmer_dashboard")
    data object AIAnalysis : Screen("ai_analysis")
    data object CreatePublication : Screen("create_publication")
    data object Profile : Screen("profile")
}
