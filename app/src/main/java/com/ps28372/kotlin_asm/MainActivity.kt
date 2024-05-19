package com.ps28372.kotlin_asm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ps28372.kotlin_asm.ui.theme.KOTLIN_ASM_PS28372Theme
import com.ps28372.kotlin_asm.view.Home
import com.ps28372.kotlin_asm.view.Login
import com.ps28372.kotlin_asm.view.Onboarding
import com.ps28372.kotlin_asm.view.Register

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KOTLIN_ASM_PS28372Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "onboarding") {
                        composable("onboarding") {
                            Onboarding(
                                onNavigateToLogin = {
                                    navController.navigate("login")
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("login") {
                            Login(
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                },
                                onNavigateHome = {
                                    navController.navigate("home")
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("register") {
                            Register(
                                onNavigateToLogin = {
                                    navController.navigate("login")
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("home") {
                            Home(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
