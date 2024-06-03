package com.ps28372.kotlin_asm.view.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.model.ProductCategory
import com.ps28372.kotlin_asm.repository.ProductRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun HomeTab(navController: NavHostController) {
    val categoriesList = remember { mutableStateOf(emptyList<ProductCategory>()) }
    val productsList = remember { mutableStateOf(emptyList<Product>()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        val productRepository = ProductRepository()
        coroutineScope {
            try {
                launch {
                    val productCategories = productRepository.getCategories()
                    categoriesList.value = productCategories
                    Log.d("HomeTab", "Categories loaded successfully, ${categoriesList.value}")
                }
                launch {
                    val products = productRepository.getProducts()
                    productsList.value = products
                    Log.d("HomeTab", "Products loaded successfully, ${productsList.value}")
                }
            } catch (e: HttpException) {
                // Handle or log the HttpException here.
                Log.e("HomeTab", "Failed to load data", e)
            }
        }
        isLoading.value = false
    }

    Box {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading.value) {
                Dialog(onDismissRequest = { /* Dialog cannot be dismissed */ }) {
                    CircularProgressIndicator(
                        progress = { 0.5f },
                        color = Color(0xff242424),
                    )
                }
            }
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Color.Black,
                            modifier = Modifier.size(21.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Make home",
                            color = Color(0xff909090),
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Beautiful".uppercase(),
                            color = Color(0xff242424),
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color(0xff808080),
                            modifier = Modifier.size(21.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow {
                    items(categoriesList.value.size) { index ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color = Color(0xffF5F5F5))
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = categoriesList.value[index].name,
                                color = Color(0xff242424),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(productsList.value.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .height(264.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { navController.navigate("productDetails/${productsList.value[index].id}") }
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .height(200.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.boarding_bg),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                    )
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .zIndex(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.add_to_card),
                                            contentDescription = "Add to cart",
                                            tint = Color.White,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = productsList.value[index].name,
                                    color = Color(0xff606060),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                                Text(
                                    text = "$ ${productsList.value[index].price}",
                                    color = Color(0xff242424),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomePreview() {
//    Home {
//        with(sharedPreferences.edit()) {
//            remove("token")
//            apply()
//        }
//        navController.popBackStack("login", false)
//    }
//}