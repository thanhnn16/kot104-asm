package com.ps28372.kotlin_asm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ps28372.kotlin_asm.ui.theme.KOTLIN_ASM_PS28372Theme
import com.ps28372.kotlin_asm.view.Onboarding
import com.ps28372.kotlin_asm.view.auth.Login
import com.ps28372.kotlin_asm.view.auth.Register
import com.ps28372.kotlin_asm.view.home.Home
import com.ps28372.kotlin_asm.view.products.Cart
import com.ps28372.kotlin_asm.view.products.ProductDetails
import com.ps28372.kotlin_asm.view.products.Search
import com.ps28372.kotlin_asm.viewmodel.HomeViewModel
import com.ps28372.kotlin_asm.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val tokenLiveData = MutableLiveData<String?>()
    private var token: String? = null
    private val productViewModelLiveData = MutableLiveData<ProductViewModel>()
    private lateinit var navController: NavHostController
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)
        tokenLiveData.value = token

        tokenLiveData.observe(this) { newToken ->
            productViewModelLiveData.value = ProductViewModel(newToken ?: "")
        }

        enableEdgeToEdge()
        setContent {
            KOTLIN_ASM_PS28372Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val startDestination = if (token == null) "onboarding" else "home"
                    navController = rememberNavController()

                    val productViewModel = productViewModelLiveData.observeAsState().value

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
                                    navController.apply {
                                        navigate("home") {
                                            popBackStack("login", inclusive = true)
                                            popBackStack("onboarding", inclusive = true)
                                        }
                                    }
                                    tokenLiveData.value = sharedPreferences.getString("token", null)
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
                            if (productViewModel != null) {
                                Home(
                                    navController = navController,
                                    onLogout = {
                                        with(sharedPreferences.edit()) {
                                            remove("token")
                                            apply()
                                        }
                                        navController.apply {
                                            navigate("login") {
                                                popUpTo("home") {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    },
                                    homeViewModel = homeViewModel,
                                    productViewModel = productViewModel
                                )
                            }
                        }
                        composable("productDetails/{productId}") { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId")
                            if (productId != null) {
                                if (productViewModel != null) {
                                    ProductDetails(
                                        navController,
                                        productId,
                                        productViewModel = productViewModel,
                                    )
                                }
                            }
                        }
                        composable("cart") {
                            Cart(navController)
                        }
                        composable("search"){
                            if (productViewModel != null) {
                                Search(navController, productViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
