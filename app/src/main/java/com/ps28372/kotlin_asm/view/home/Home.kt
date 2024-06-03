package com.ps28372.kotlin_asm.view.home

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.utils.BottomNavScreen
import com.ps28372.kotlin_asm.viewmodel.HomeViewModel

val items = listOf(
    BottomNavScreen.Home,
    BottomNavScreen.Favorites,
    BottomNavScreen.Notifications,
    BottomNavScreen.Profile,
)

@Composable
fun Home(navController: NavHostController, onLogout: () -> Unit, homeViewModel: HomeViewModel) {
    val bottomNavController: NavHostController = rememberNavController()
    Scaffold(containerColor = Color.White, bottomBar = {
        BottomNavigationBar(navController = bottomNavController)
    }) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Home.route) { HomeTab(navController, homeViewModel) }
            composable(BottomNavScreen.Favorites.route) { FavoritesTab(navController) }
            composable(BottomNavScreen.Notifications.route) { NotificationsTab(navController) }
            composable(BottomNavScreen.Profile.route) { ProfileTab(onLogout) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White, modifier = Modifier.height(75.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(icon = {
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                when (screen) {
                    BottomNavScreen.Home -> {
                        Icon(painter = painterResource(
                            if (isSelected) R.drawable.ic_home_filled else R.drawable.ic_home
                        ),
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Color(0xff242424)
                            } else {
                                Color.Unspecified
                            })
                    }

                    BottomNavScreen.Favorites -> {
                        Icon(painter = painterResource(
                            if (isSelected) R.drawable.ic_save_filled else R.drawable.ic_save
                        ),
                            contentDescription = "Favorites",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Color(0xff242424)
                            } else {
                                Color.Unspecified
                            })
                    }

                    BottomNavScreen.Notifications -> {
                        Icon(painter = painterResource(
                            if (isSelected) R.drawable.ic_notification_filled else R.drawable.ic_notification
                        ),
                            contentDescription = "Notifications",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Color(0xff242424)
                            } else {
                                Color.Unspecified
                            })
                    }

                    BottomNavScreen.Profile -> {
                        Icon(painter = painterResource(
                            if (isSelected) R.drawable.ic_profile_filled else R.drawable.ic_profile
                        ),
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Color(0xff242424)
                            } else {
                                Color.Unspecified
                            })
                    }
                }
            },
                label = null,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

// docs: https://developer.android.com/develop/ui/compose/navigation#bottom-nav
