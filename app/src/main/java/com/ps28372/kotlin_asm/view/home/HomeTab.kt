package com.ps28372.kotlin_asm.view.home

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.utils.BASE_URL
import com.ps28372.kotlin_asm.viewmodel.HomeViewModel

@Composable
fun HomeTab(navController: NavHostController, homeViewModel: HomeViewModel) {
    val isLoading = homeViewModel.isLoading.observeAsState(initial = true)
    val productCategories = homeViewModel.productCategories.observeAsState(initial = emptyList())
    val products = homeViewModel.products.observeAsState(initial = emptyList())
    val context = LocalContext.current

    Box {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading.value) {
                Dialog(onDismissRequest = { /* Dialog cannot be dismissed */ }) {
                    CircularProgressIndicator(
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
                Row {
                    var selectedCategory by rememberSaveable { mutableIntStateOf(-1) }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (selectedCategory == -1) Color(0xff303030) else Color(
                                        0xffF5F5F5
                                    )
                                )
                        ) {
                            IconButton(onClick = {
                                selectedCategory = -1
                                homeViewModel.loadData()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "All",
                            color = Color(0xff242424),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    LazyRow {
                        items(productCategories.value.size) { index ->
                            val imgUrl = "$BASE_URL${productCategories.value[index].icon}"
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedCategory == index) Color(0xff303030) else Color(
                                                0xffF5F5F5
                                            )
                                        )
                                ) {
                                    IconButton(onClick = {
                                        selectedCategory = index
                                        homeViewModel.getProductsByCategory(
                                            productCategories.value[index].id
                                        )
                                    }) {
                                        AsyncImage(
                                            model = imgUrl,
                                            contentDescription = null,
                                            imageLoader = ImageLoader.Builder(context)
                                                .components {
                                                    add(SvgDecoder.Factory())
                                                }
                                                .build(),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(28.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = productCategories.value[index].name,
                                    color = Color(0xff242424),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (products.value.isNotEmpty()) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxSize(),
                        columns = GridCells.Fixed(2),
                    ) {
                        items(products.value.size) { index ->
                            val product: Product = products.value?.get(index) ?: Product(
                                0,
                                0,
                                "",
                                "",
                                "",
                                false,
                                emptyList(),
                                emptyList()
                            )
                            Box(modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .height(264.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    val id = product.id
                                    navController.navigate(
                                        "productDetails/${id}"
                                    )
                                }) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .height(200.dp)
                                    ) {
                                        val firstImgUrl =
                                            "$BASE_URL${product.images[0].imageUrl}"
                                        AsyncImage(
                                            model = firstImgUrl,
                                            placeholder = painterResource(id = R.drawable.boarding_bg),
                                            error = painterResource(id = R.drawable.boarding_bg),
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
                                        text = products.value[index].name,
                                        color = Color(0xff606060),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                    )
                                    Text(
                                        text = "$ ${products.value[index].price}",
                                        color = Color(0xff242424),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No products found",
                        color = Color(0xff242424),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
