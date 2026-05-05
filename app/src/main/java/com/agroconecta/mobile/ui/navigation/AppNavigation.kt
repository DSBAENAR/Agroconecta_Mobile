package com.agroconecta.mobile.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.agroconecta.mobile.data.session.SessionManager
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.data.session.UserSession
import com.agroconecta.mobile.ui.components.AgroBottomNavBar
import com.agroconecta.mobile.ui.screens.buyer.BuyerDashboardScreen
import com.agroconecta.mobile.ui.screens.farmer.FarmerDashboardScreen
import com.agroconecta.mobile.ui.screens.home.HomeScreen
import com.agroconecta.mobile.ui.screens.login.LoginScreen
import com.agroconecta.mobile.ui.screens.marketplace.MarketplaceScreen
import com.agroconecta.mobile.ui.screens.onboarding.RoleSelectionScreen
import com.agroconecta.mobile.ui.screens.onboarding.WelcomeScreen
import com.agroconecta.mobile.ui.screens.product.FarmerDetailScreen
import com.agroconecta.mobile.ui.screens.product.ProductDetailScreen
import com.agroconecta.mobile.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    val session = SessionManager.session

    val bottomBarRoutes = listOf(
        Screen.BuyerHome.route,
        Screen.FarmerHome.route,
        Screen.Marketplace.route,
        Screen.BuyerDashboard.route,
        Screen.FarmerDashboard.route,
        Screen.Profile.route
    )

    val showBottomBar = session != null && currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AgroBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogout = {
                        // ✅ LOGOUT REAL
                        SessionManager.clearSession()

                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(padding)
        ) {

            // ---------------- WELCOME ----------------
            composable(Screen.Welcome.route) {
                WelcomeScreen {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            }

            // ---------------- ROLE ----------------
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

            // ---------------- LOGIN BUYER ----------------
            composable(Screen.LoginBuyer.route) {
                LoginScreen(
                    isFarmer = false,
                    onLoginClick = { email, _ ->

                        SessionManager.saveSession(
                            UserSession(
                                userId = 1,
                                name = "Buyer Demo",
                                email = email,
                                phone = "0000000000",
                                role = UserRole.BUYER,
                                token = "fake-token"
                            )
                        )

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

            // ---------------- LOGIN FARMER ----------------
            composable(Screen.LoginFarmer.route) {
                LoginScreen(
                    isFarmer = true,
                    onLoginClick = { email, _ ->

                        SessionManager.saveSession(
                            UserSession(
                                userId = 2,
                                name = "Farmer Demo",
                                email = email,
                                phone = "0000000000",
                                role = UserRole.FARMER,
                                token = "fake-token"
                            )
                        )

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

            // ---------------- HOME ----------------
            composable(Screen.BuyerHome.route) {
                HomeScreen(
                    onExploreClick = {
                        navController.navigate(Screen.Marketplace.route)
                    },
                    onFarmerClick = {
                        navController.navigate(Screen.LoginFarmer.route)
                    },
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    }
                )
            }

            composable(Screen.FarmerHome.route) {
                HomeScreen(
                    onExploreClick = {
                        navController.navigate(Screen.Marketplace.route)
                    },
                    onFarmerClick = {},
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    }
                )
            }

            // ---------------- MARKET ----------------
            composable(Screen.Marketplace.route) {
                MarketplaceScreen(
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    },
                    onFilterClick = {}
                )
            }

            // ---------------- PRODUCT DETAIL ----------------
            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(
                    navArgument("productId") { type = NavType.StringType }
                )
            ) { entry ->

                val productId = entry.arguments?.getString("productId").orEmpty()

                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = {},
                    onContactFarmerClick = {},
                    onViewFarmerClick = { farmerId ->
                        navController.navigate(Screen.FarmerDetail.createRoute(farmerId))
                    }
                )
            }

            // ---------------- FARMER DETAIL ----------------
            composable(
                route = Screen.FarmerDetail.route,
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { entry ->

                val farmerId = entry.arguments?.getString("farmerId").orEmpty()

                FarmerDetailScreen(
                    farmerId = farmerId,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    }
                )
            }

            // ---------------- PROFILE ----------------
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onEditProfileClick = {}
                )
            }

            // ---------------- DASHBOARDS ----------------
            composable(Screen.BuyerDashboard.route) {
                BuyerDashboardScreen()
            }

            composable(Screen.FarmerDashboard.route) {
                FarmerDashboardScreen(
                    onAddProductClick = {},
                    onAnalysisClick = {}
                )
            }
        }
    }
}