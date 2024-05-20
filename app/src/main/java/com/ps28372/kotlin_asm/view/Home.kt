package com.ps28372.kotlin_asm.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

val items = listOf(
    BottomNavScreen.Home,
    BottomNavScreen.Favorites,
    BottomNavScreen.Notifications,
    BottomNavScreen.Profile,
)

@Composable
fun HomeTab(navController: NavHostController) {
    Text(text = "Home")
}

@Composable
fun Favorites(navController: NavHostController) {
    Text(text = "Favorites")
}

@Composable
fun Notifications(navController: NavHostController) {
    Text(text = "Notifications")
}

@Composable
fun Profile(navController: NavHostController) {
    Text(text = "Profile")
}

@Composable
fun Home(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                BottomNavigationItem(icon = {
                    Icon(
                        imageVector = when (screen) {
                            BottomNavScreen.Home -> Icons.Default.Home
                            BottomNavScreen.Favorites -> Icons.Default.Favorite
                            BottomNavScreen.Notifications -> Icons.Default.Notifications
                            BottomNavScreen.Profile -> Icons.Default.Person
                        },
                        contentDescription = null
                    )
                },
                    label = { Text(screen.title) },
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
            composable(BottomNavScreen.Favorites.route) { Favorites(navController) }
            composable(BottomNavScreen.Notifications.route) { Notifications(navController) }
            composable(BottomNavScreen.Profile.route) { Profile(navController) }
        }
    }
}

// docs: https://developer.android.com/develop/ui/compose/navigation#bottom-nav
