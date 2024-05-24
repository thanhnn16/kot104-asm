package com.ps28372.kotlin_asm.view.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.utils.BottomNavScreen

val items = listOf(
    BottomNavScreen.Home,
    BottomNavScreen.Favorites,
    BottomNavScreen.Notifications,
    BottomNavScreen.Profile,
)

@Composable
fun Home() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                modifier = Modifier.height(75.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(icon = {
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        when (screen) {
                            BottomNavScreen.Home -> {
                                Icon(
                                    painter = painterResource(
                                        if (isSelected) R.drawable.ic_home_filled else R.drawable.ic_home
                                    ),
                                    contentDescription = "Home",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                        Color(0xff242424)
                                    } else {
                                        Color.Unspecified
                                    }
                                )
                            }

                            BottomNavScreen.Favorites -> {
                                Icon(
                                    painter = painterResource(
                                        if (isSelected) R.drawable.ic_save_filled else R.drawable.ic_save
                                    ),
                                    contentDescription = "Favorites",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                        Color(0xff242424)
                                    } else {
                                        Color.Unspecified
                                    }
                                )
                            }

                            BottomNavScreen.Notifications -> {
                                Icon(
                                    painter = painterResource(
                                        if (isSelected) R.drawable.ic_notification_filled else R.drawable.ic_notification
                                    ),
                                    contentDescription = "Notifications",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                        Color(0xff242424)
                                    } else {
                                        Color.Unspecified
                                    }
                                )
                            }

                            BottomNavScreen.Profile -> {
                                Icon(
                                    painter = painterResource(
                                        if (isSelected) R.drawable.ic_profile_filled else R.drawable.ic_profile
                                    ),
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                        Color(0xff242424)
                                    } else {
                                        Color.Unspecified
                                    }
                                )
                            }
                        }
                    },
                        label = null,
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        })
                }
            }
        }) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Home.route) { HomeTab(navController) }
            composable(BottomNavScreen.Favorites.route) { FavoritesTab(navController) }
            composable(BottomNavScreen.Notifications.route) { NotificationsTab(navController) }
            composable(BottomNavScreen.Profile.route) { ProfileTab(navController) }
        }
    }
}

// docs: https://developer.android.com/develop/ui/compose/navigation#bottom-nav
