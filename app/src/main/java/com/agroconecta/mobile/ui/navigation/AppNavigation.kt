package com.agroconecta.mobile.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.agroconecta.mobile.data.session.UserRole
import com.agroconecta.mobile.ui.components.AgroBottomNavBar
import com.agroconecta.mobile.ui.screens.buyer.BuyerDashboardScreen
import com.agroconecta.mobile.ui.screens.farmer.AIAnalysisScreen
import com.agroconecta.mobile.ui.screens.farmer.CreatePublicationScreen
import com.agroconecta.mobile.ui.screens.farmer.FarmerAccountScreen
import com.agroconecta.mobile.ui.screens.farmer.FarmerDashboardScreen
import com.agroconecta.mobile.ui.screens.farmer.FarmerHomeScreen
import com.agroconecta.mobile.ui.screens.home.HomeScreen
import com.agroconecta.mobile.ui.screens.login.LoginScreen
import com.agroconecta.mobile.ui.screens.marketplace.MarketplaceScreen
import com.agroconecta.mobile.ui.screens.onboarding.RoleSelectionScreen
import com.agroconecta.mobile.ui.screens.onboarding.WelcomeScreen
import com.agroconecta.mobile.ui.screens.product.FarmerDetailScreen
import com.agroconecta.mobile.ui.screens.product.ProductDetailScreen
import com.agroconecta.mobile.ui.screens.profile.ProfileScreen
import com.agroconecta.mobile.ui.viewmodel.UserViewModel
import com.agroconecta.mobile.ui.screens.*
import com.agroconecta.mobile.ui.screens.cart.CartScreen
import com.agroconecta.mobile.ui.screens.notifications.NotificationsScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val userViewModel: UserViewModel = hiltViewModel()

    val session = userViewModel.session
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    val bottomBarRoutes = listOf(
        Screen.BuyerHome.route,
        Screen.FarmerHome.route,
        Screen.Marketplace.route,
        Screen.BuyerDashboard.route,
        Screen.FarmerDashboard.route,
        Screen.Profile.route,
        Screen.Cart.route
    )

    val showBottomBar = session != null && currentRoute in bottomBarRoutes

    // ✅ Navegación automática cuando hay sesión
    LaunchedEffect(session) {
        if (session != null) {
            val route = when (session.role) {
                UserRole.BUYER -> Screen.BuyerHome.route
                UserRole.FARMER -> Screen.FarmerHome.route

                UserRole.ADMIN -> Screen.Welcome.route
                UserRole.UNKNOWN -> Screen.Welcome.route
            }

            navController.navigate(route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

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
                        userViewModel.logout()

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
                    onLoginClick = { email, password ->
                        userViewModel.login(email, password)
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
                    onLoginClick = { email, password ->
                        userViewModel.login(email, password, isFarmer = true)
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
                FarmerHomeScreen(
                    onPublishClick = {
                        navController.navigate(Screen.CreatePublication.route)
                    },
                    onProductClick = { id ->
                        navController.navigate(Screen.ProductDetail.createRoute(id))
                    },
                    onNotificationsClick = {
                        navController.navigate(Screen.Notifications.route)
                    }
                )
            }

            composable(Screen.Notifications.route) {
                NotificationsScreen(
                    onBackClick = { navController.popBackStack() }
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
                    onContactFarmerClick = {},
                    onViewFarmerClick = { farmerId ->
                        navController.navigate(Screen.FarmerDetail.createRoute(farmerId))
                    },
                    onPublishSimilarClick = {
                        navController.navigate(Screen.CreatePublication.route)
                    }
                )
            }

            composable(Screen.Cart.route) {
                CartScreen()
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
                    onEditProfileClick = {},
                    onAccountClick = {
                        navController.navigate(Screen.FarmerAccount.route)
                    }
                )
            }

            // ---------------- DASHBOARDS ----------------
            composable(Screen.BuyerDashboard.route) {
                BuyerDashboardScreen()
            }

            composable(Screen.FarmerDashboard.route) {
                FarmerDashboardScreen(
                    onAddProductClick = {
                        navController.navigate(Screen.CreatePublication.route)
                    },
                    onAnalysisClick = {
                        navController.navigate(Screen.AIAnalysis.route)
                    }
                )
            }

            // ---------------- FARMER TOOLS ----------------
            composable(Screen.CreatePublication.route) {
                CreatePublicationScreen(
                    onBackClick = { navController.popBackStack() },
                    onPublishClick = { navController.popBackStack() }
                )
            }

            composable(Screen.AIAnalysis.route) {
                AIAnalysisScreen()
            }

            composable(Screen.FarmerAccount.route) {
                FarmerAccountScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}