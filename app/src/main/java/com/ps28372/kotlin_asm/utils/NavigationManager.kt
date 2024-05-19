package com.ps28372.kotlin_asm.utils//import androidx.compose.runtime.remember
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.createGraph
//import com.ps28372.kotlin_asm.view.Login
//import com.ps28372.kotlin_asm.view.Onboarding
//
//
//val navController = rememberNavController()
//
//val navGraph by remember (navController){
//    navController.createGraph(startDestination = "onboarding") {
//        composable("onboarding") {
//            Onboarding(
//                onNavigateToLogin = {
//                    navController.navigate("login")
//                }
//            )
//        }
//        composable("login") {
//            Login(
//                onNavigateToRegister = {
//                    navController.navigate("register")
//                }
//            )
//        }
//    }
//}