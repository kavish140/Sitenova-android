package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuoteScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.SiteNovaViewModel

const val ROUTE_AUTH = "auth"
const val ROUTE_HOME = "home"
const val ROUTE_SERVICES = "services"
const val ROUTE_SHOWCASE = "showcase"
const val ROUTE_QUOTE = "quote"
const val ROUTE_PROFILE = "profile"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteNovaApp(
    viewModel: SiteNovaViewModel,
    startRoute: String,
    onAuthSuccess: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: startRoute

    val topAppBarBrandBrush = remember {
        Brush.linearGradient(
            colors = listOf(AccentSky, AccentPink)
        )
    }

    // ROBUST ERROR BOUNDARY STATE (Reminiscent of ErrorBoundary.tsx)
    val globalError = viewModel.globalAppError

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(brush = topAppBarBrandBrush),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "SN",
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "SITE NOVA",
                                style = MaterialTheme.typography.titleMedium,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(ROUTE_PROFILE) }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile & Settings",
                                tint = AccentSky
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkBackground,
                        titleContentColor = PrimaryWhite
                    ),
                    modifier = Modifier.border(0.dp, Color.Transparent) // Border elimination
                )
            },
            bottomBar = {
                if (currentRoute != ROUTE_AUTH) {
                    // Adaptive / Cozy M3 bottom navigation bar
                    NavigationBar(
                    containerColor = Color(0xFF0F0F0F),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .testTag("site_nova_bottom_nav"),
                    contentColor = TextPrimary
                ) {
                    // Home
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_HOME,
                        onClick = {
                            if (currentRoute != ROUTE_HOME) {
                                navController.navigate(ROUTE_HOME) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == ROUTE_HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AccentSky,
                            indicatorColor = AccentSky.copy(alpha = 0.2f),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    // Services
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_SERVICES,
                        onClick = {
                            if (currentRoute != ROUTE_SERVICES) {
                                navController.navigate(ROUTE_SERVICES) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == ROUTE_SERVICES) Icons.Filled.BusinessCenter else Icons.Outlined.BusinessCenter,
                                contentDescription = "Services"
                            )
                        },
                        label = { Text("Services", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AccentSky,
                            indicatorColor = AccentSky.copy(alpha = 0.2f),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_services")
                    )

                    // Estimate Quote Form
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_QUOTE,
                        onClick = {
                            if (currentRoute != ROUTE_QUOTE) {
                                navController.navigate(ROUTE_QUOTE) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == ROUTE_QUOTE) Icons.Filled.PriceChange else Icons.Outlined.PriceChange,
                                contentDescription = "Estimate"
                            )
                        },
                        label = { Text("Estimate", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AccentSky,
                            indicatorColor = AccentSky.copy(alpha = 0.2f),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_quote")
                    )

                    // Showcase Sites
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_SHOWCASE,
                        onClick = {
                            if (currentRoute != ROUTE_SHOWCASE) {
                                navController.navigate(ROUTE_SHOWCASE) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == ROUTE_SHOWCASE) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Showcase"
                            )
                        },
                        label = { Text("Showcase", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AccentSky,
                            indicatorColor = AccentSky.copy(alpha = 0.2f),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_showcase")
                    )

                    // Profile / Scheduling Hub
                    NavigationBarItem(
                        selected = currentRoute == ROUTE_PROFILE,
                        onClick = {
                            if (currentRoute != ROUTE_PROFILE) {
                                navController.navigate(ROUTE_PROFILE) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == ROUTE_PROFILE) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                                contentDescription = "Schedules"
                            )
                        },
                        label = { Text("Schedules", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = AccentSky,
                            indicatorColor = AccentSky.copy(alpha = 0.2f),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_profile")
                    )
                }
                }
            },
            containerColor = DarkBackground
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(ROUTE_AUTH) {
                    com.example.ui.screens.AuthScreen(
                        viewModel = viewModel,
                        onAuthSuccess = {
                            onAuthSuccess()
                            navController.navigate(ROUTE_HOME) {
                                popUpTo(ROUTE_AUTH) { inclusive = true }
                            }
                        }
                    )
                }

                composable(ROUTE_HOME) {
                    HomeScreen(
                        onNavigateToQuote = {
                            navController.navigate(ROUTE_QUOTE) {
                                launchSingleTop = true
                            }
                        },
                        onNavigateToServices = {
                            navController.navigate(ROUTE_SERVICES) {
                                launchSingleTop = true
                            }
                        },
                        onShowLinkError = { errorMsg ->
                            viewModel.globalAppError = errorMsg
                        }
                    )
                }

                composable(ROUTE_SERVICES) {
                    ServicesScreen(
                        onNavigateToQuote = { targetScopeName ->
                            viewModel.quoteProjectScope = when {
                                targetScopeName.contains("Ecommerce", ignoreCase = true) -> "Custom Enterprise Integration (From Rs. 40,000)"
                                targetScopeName.contains("Web", ignoreCase = true) -> "Business Discovery Suite (From Rs. 15,000)"
                                else -> "Starter Landing (From Rs. 5,000)"
                            }
                            navController.navigate(ROUTE_QUOTE) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(ROUTE_QUOTE) {
                    QuoteScreen(viewModel = viewModel)
                }

                composable(ROUTE_SHOWCASE) {
                    com.example.ui.screens.ShowcaseScreen(
                         onShowLinkError = { errorMsg ->
                             viewModel.globalAppError = errorMsg
                         }
                    )
                }

                composable(ROUTE_PROFILE) {
                    ProfileScreen(
                        viewModel = viewModel,
                        onLogout = {
                            onLogout()
                            navController.navigate(ROUTE_AUTH) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }

        // CUSTOM ERROR BOUNDARY OVERLAY DIAGNOSTICS (ErrorBoundary.tsx analog)
        globalError?.let { err ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.dp, ErrorRed, RoundedCornerShape(24.dp))
                        .background(DarkSurface)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "System Interrupted",
                        tint = ErrorRed,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "UI Node Interruption",
                        color = PrimaryWhite,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "SiteNova caught a local workspace database or stream processing error safely. The system boundary preserved UI stack layers.",
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceElevated)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = err,
                            color = ErrorRed,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            textAlign = TextAlign.Left
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.dismissGlobalError() },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                    ) {
                        Text(
                            text = "Reset Operation Stream",
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
