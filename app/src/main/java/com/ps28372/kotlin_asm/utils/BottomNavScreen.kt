package com.ps28372.kotlin_asm.utils

sealed class BottomNavScreen(val route: String, val title: String) {
    data object Home : BottomNavScreen("homeTab", "Home")
    data object Favorites : BottomNavScreen("favoritesTab", "Favorites")
    data object Notifications : BottomNavScreen("notificationsTab", "Notifications")
    data object Profile : BottomNavScreen("profileTab", "Profile")
}