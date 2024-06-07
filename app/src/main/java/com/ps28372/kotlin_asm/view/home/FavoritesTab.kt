package com.ps28372.kotlin_asm.view.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.utils.CartHelper
import com.ps28372.kotlin_asm.viewmodel.ProductViewModel

@Composable
fun FavoritesTab(navController: NavHostController, productViewModel: ProductViewModel) {
    val isLoading = productViewModel.isLoading.observeAsState(initial = true)
    val favoriteProducts = productViewModel.favoriteProducts.observeAsState(initial = emptyList())
    val context = LocalContext.current

    val cartHelper = CartHelper(context)

    LaunchedEffect(key1 = Unit) {
        productViewModel.getFavoriteProducts()
    }

    Log.d("Favorite", "FavoritesTab: ${favoriteProducts.value}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        if (isLoading.value) {
            Dialog(onDismissRequest = { /* Dialog cannot be dismissed */ }) {
                CircularProgressIndicator(
                    color = Color(0xff242424),
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        Modifier.size(20.dp)
                    )
                }
                Text(text = "Favorites", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    navController.navigate("cart")
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Search",
                        Modifier.size(20.dp)
                    )
                }
            }
            if (favoriteProducts.value.isNullOrEmpty()) {
                Text(
                    text = "No favorite products",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 64.dp).align(Alignment.CenterHorizontally),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff606060),
                )
            }
            LazyColumn(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 70.dp
                )
            ) {
                items(favoriteProducts.value?.size ?: 0) { index ->
                    val product = favoriteProducts.value?.get(index)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(100.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        AsyncImage(
                            model = product?.getFirstImageUrl(),
                            placeholder = painterResource(id = R.drawable.boarding_bg),
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth(0.9f),
                        ) {
                            product?.name?.let {
                                Text(
                                    text = it,
                                    fontSize = 14.sp,
                                    color = Color(0xff606060),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$ ${product?.price}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(1f),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                            ) {
                                IconButton(onClick = {
                                    product?.id?.let { productViewModel.removeFavoriteProduct(it) }
                                }) {
                                    Image(
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = "Remove from favorites",
                                        modifier = Modifier
                                            .size(16.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                            Image(
                                painter = painterResource(id = R.drawable.add_to_card),
                                contentDescription = "Add to cart",
                                modifier = Modifier.clickable {
                                    product?.let { cartHelper.addItem(it) }
                                    cartHelper.saveCart(context)
                                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        color = Color(0xffF0F0F0)
                    )
                }
            }
        }
        if (favoriteProducts.value.isNullOrEmpty()) {
            return
        }
        Button(
            onClick = {
                favoriteProducts.value?.forEach {
                    cartHelper.addItem(it)
                }
                cartHelper.saveCart(context)
                Toast.makeText(context, "Added all to cart", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff242424),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .align(alignment = Alignment.BottomCenter)
                .zIndex(99f)
        ) {
            Text(text = "Add all to my cart", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }
    }
}