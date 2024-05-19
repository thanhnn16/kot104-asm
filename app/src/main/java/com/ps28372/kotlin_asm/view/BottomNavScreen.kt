package com.ps28372.kotlin_asm.view

sealed class BottomNavScreen(val route: String) {
    data object Home : BottomNavScreen("home")
    data object Favorites : BottomNavScreen("favorites")
    data object Notifications : BottomNavScreen("notifications")
    data object Profile : BottomNavScreen("profile")
    data object Search : BottomNavScreen("search")
    data object Cart : BottomNavScreen("cart")
}