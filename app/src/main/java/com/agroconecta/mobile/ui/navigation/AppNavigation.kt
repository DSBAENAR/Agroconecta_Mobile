package com.agroconecta.mobile.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agroconecta.mobile.ui.components.BuyerBottomNavBar
import com.agroconecta.mobile.ui.components.FarmerBottomNavBar
import com.agroconecta.mobile.ui.screens.buyer.BuyerDashboardScreen
import com.agroconecta.mobile.ui.screens.farmer.AIAnalysisScreen
import com.agroconecta.mobile.ui.screens.farmer.CreatePublicationScreen
import com.agroconecta.mobile.ui.screens.farmer.FarmerDashboardScreen
import com.agroconecta.mobile.ui.screens.home.HomeScreen
import com.agroconecta.mobile.ui.screens.login.LoginScreen
import com.agroconecta.mobile.ui.screens.marketplace.MarketplaceScreen
import com.agroconecta.mobile.ui.screens.onboarding.RoleSelectionScreen
import com.agroconecta.mobile.ui.screens.onboarding.WelcomeScreen
import com.agroconecta.mobile.ui.screens.register.RegisterScreen
import com.agroconecta.mobile.ui.screens.product.ProductDetailScreen
import com.agroconecta.mobile.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    // Track if the user is a farmer (set after login)
    var isFarmerUser by remember { mutableStateOf(false) }

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
                                popUpTo(if (isFarmerUser) Screen.FarmerHome.route else Screen.BuyerHome.route) {
                                    saveState = true
                                }
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
                                popUpTo(Screen.BuyerHome.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onContinueClick = {
                        navController.navigate(Screen.RoleSelection.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
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
                    onGoogleClick = { },
                    onRegisterClick = {
                        navController.navigate(Screen.RegisterBuyer.route)
                    },
                    onForgotPasswordClick = { }
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
                    onGoogleClick = { },
                    onRegisterClick = {
                        navController.navigate(Screen.RegisterFarmer.route)
                    },
                    onForgotPasswordClick = { },
                    onSmsCodeClick = { }
                )
            }

            composable(Screen.RegisterBuyer.route) {
                RegisterScreen(
                    isFarmer = false,
                    onRegisterClick = {
                        isFarmerUser = false
                        navController.navigate(Screen.BuyerHome.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onLoginClick = { navController.popBackStack() }
                )
            }

            composable(Screen.RegisterFarmer.route) {
                RegisterScreen(
                    isFarmer = true,
                    onRegisterClick = {
                        isFarmerUser = true
                        navController.navigate(Screen.FarmerHome.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onLoginClick = { navController.popBackStack() }
                )
            }

            composable(Screen.BuyerHome.route) {
                HomeScreen(
                    onExploreClick = { navController.navigate(Screen.Marketplace.route) },
                    onFarmerClick = {
                        navController.navigate(Screen.LoginFarmer.route) {
                            popUpTo(Screen.BuyerHome.route) { inclusive = true }
                        }
                    },
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(Screen.FarmerHome.route) {
                HomeScreen(
                    onExploreClick = { navController.navigate(Screen.Marketplace.route) },
                    onFarmerClick = { },
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
                    onFilterClick = { }
                )
            }

            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = { },
                    onContactFarmerClick = { },
                    onViewFarmerClick = { navController.navigate(Screen.Profile.route) }
                )
            }

            composable(Screen.BuyerDashboard.route) {
                BuyerDashboardScreen()
            }

            composable(Screen.FarmerDashboard.route) {
                FarmerDashboardScreen(
                    onAddProductClick = { navController.navigate(Screen.CreatePublication.route) },
                    onAnalysisClick = { navController.navigate(Screen.AIAnalysis.route) }
                )
            }

            composable(Screen.CreatePublication.route) {
                CreatePublicationScreen(
                    onBackClick = { navController.popBackStack() },
                    onPublishClick = { navController.popBackStack() }
                )
            }

            composable(Screen.AIAnalysis.route) {
                AIAnalysisScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    isFarmer = isFarmerUser,
                    onEditProfileClick = { }
                )
            }
        }
    }
}
