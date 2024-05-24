package com.ps28372.kotlin_asm

import android.content.SharedPreferences
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
import com.ps28372.kotlin_asm.view.Onboarding
import com.ps28372.kotlin_asm.view.auth.Login
import com.ps28372.kotlin_asm.view.auth.Register
import com.ps28372.kotlin_asm.view.home.Home

class MainActivity : ComponentActivity() {
    private val context = this
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("token", MODE_PRIVATE)

    private val token = sharedPreferences.getString("token", null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KOTLIN_ASM_PS28372Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    val startDestination = if (token == null) "onboarding" else "home"

                    NavHost(navController, startDestination = startDestination) {
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
                            Home()
                        }
                    }
                }
            }
        }
    }
}
