package com.agroconecta.mobile.ui.navigation

sealed class Screen(val route: String) {

    object Welcome : Screen("welcome")
    object RoleSelection : Screen("role_selection")

    object LoginBuyer : Screen("login_buyer")
    object LoginFarmer : Screen("login_farmer")

    object RegisterBuyer : Screen("register_buyer")
    object RegisterFarmer : Screen("register_farmer")

    object BuyerHome : Screen("buyer_home")
    object FarmerHome : Screen("farmer_home")

    object Marketplace : Screen("marketplace")

    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }

    object FarmerDetail : Screen("farmer_detail/{farmerId}") {
        fun createRoute(farmerId: String) = "farmer_detail/$farmerId"
    }

    object Profile : Screen("profile")

    object BuyerDashboard : Screen("buyer_dashboard")
    object FarmerDashboard : Screen("farmer_dashboard")

    object CreatePublication : Screen("create_publication")
    object AIAnalysis : Screen("ai_analysis")
    object FarmerAccount : Screen("farmer_account")

    object Cart : Screen("cart")
}