package com.agroconecta.mobile.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.agroconecta.mobile.ui.components.BuyerBottomNavBar
import com.agroconecta.mobile.ui.components.FarmerBottomNavBar
import com.agroconecta.mobile.ui.screens.buyer.BuyerDashboardScreen
import com.agroconecta.mobile.ui.screens.farmer.*
import com.agroconecta.mobile.ui.screens.home.HomeScreen
import com.agroconecta.mobile.ui.screens.login.LoginScreen
import com.agroconecta.mobile.ui.screens.marketplace.MarketplaceScreen
import com.agroconecta.mobile.ui.screens.onboarding.*
import com.agroconecta.mobile.ui.screens.product.*
import com.agroconecta.mobile.ui.screens.profile.ProfileScreen
import com.agroconecta.mobile.ui.screens.register.RegisterScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    var isFarmerUser by rememberSaveable { mutableStateOf(false) }

    val showBottomBar = currentRoute in listOf(
        Screen.BuyerHome.route,
        Screen.FarmerHome.route,
        Screen.Marketplace.route,
        Screen.BuyerDashboard.route,
        Screen.FarmerDashboard.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                if (isFarmerUser) {
                    FarmerBottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.FarmerHome.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                } else {
                    BuyerBottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.BuyerHome.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Welcome.route) {
                WelcomeScreen {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            }

            composable(Screen.RoleSelection.route) {
                RoleSelectionScreen(
                    onBuyerClick = {
                        navController.navigate(Screen.LoginBuyer.route)
                    },
                    onSellerClick = {
                        navController.navigate(Screen.LoginFarmer.route)
                    }
                )
            }

            composable(Screen.LoginBuyer.route) {
                LoginScreen(
                    isFarmer = false,
                    onLoginClick = {
                        isFarmerUser = false
                        navController.navigate(Screen.BuyerHome.route) {
                            popUpTo(Screen.LoginBuyer.route) { inclusive = true }
                        }
                    },
                    onGoogleClick = {},
                    onRegisterClick = {
                        navController.navigate(Screen.RegisterBuyer.route)
                    },
                    onForgotPasswordClick = {}
                )
            }

            composable(Screen.LoginFarmer.route) {
                LoginScreen(
                    isFarmer = true,
                    onLoginClick = {
                        isFarmerUser = true
                        navController.navigate(Screen.FarmerHome.route) {
                            popUpTo(Screen.LoginFarmer.route) { inclusive = true }
                        }
                    },
                    onGoogleClick = {},
                    onRegisterClick = {
                        navController.navigate(Screen.RegisterFarmer.route)
                    },
                    onForgotPasswordClick = {},
                    onSmsCodeClick = {}
                )
            }

            composable(Screen.BuyerHome.route) {
                HomeScreen(
                    onExploreClick = {
                        navController.navigate(Screen.Marketplace.route)
                    },
                    onFarmerClick = {
                        navController.navigate(Screen.LoginFarmer.route)
                    },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(Screen.FarmerHome.route) {
                HomeScreen(
                    onExploreClick = {
                        navController.navigate(Screen.Marketplace.route)
                    },
                    onFarmerClick = {},
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(Screen.Marketplace.route) {
                MarketplaceScreen(
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onFilterClick = {}
                )
            }

            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { entry ->

                val productId = entry.arguments?.getString("productId").orEmpty()

                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = {},
                    onContactFarmerClick = {},
                    onViewFarmerClick = { farmerId ->
                        navController.navigate(
                            Screen.FarmerDetail.createRoute(farmerId)
                        )
                    }
                )
            }

            composable(
                route = Screen.FarmerDetail.route,
                arguments = listOf(navArgument("farmerId") { type = NavType.StringType })
            ) { entry ->

                val farmerId = entry.arguments?.getString("farmerId").orEmpty()

                FarmerDetailScreen(
                    farmerId = farmerId,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate(
                            Screen.ProductDetail.createRoute(productId)
                        )
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(isFarmer = isFarmerUser, onEditProfileClick = {})
            }
        }
    }
}