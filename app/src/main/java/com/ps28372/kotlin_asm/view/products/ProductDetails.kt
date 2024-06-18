package com.ps28372.kotlin_asm.view.products

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.model.Product
import com.ps28372.kotlin_asm.utils.BASE_URL
import com.ps28372.kotlin_asm.utils.CartHelper
import com.ps28372.kotlin_asm.viewmodel.ProductViewModel
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetails(
    navController: NavHostController, productId: String, productViewModel: ProductViewModel
) {
    var qty by remember {
        mutableIntStateOf(1)
    }

    val context = LocalContext.current

    val cartHelper = CartHelper(context)

    val isLoading = productViewModel.isLoading.observeAsState(initial = true)

    var reloadProduct by remember {
        mutableStateOf(false)
    }

    val product = productViewModel.product.observeAsState(
        initial = Product(
            id = 0,
            categoryId = 0,
            name = "",
            description = "",
            price = "",
            images = listOf(),
            reviews = listOf()
        )
    )

    LaunchedEffect(key1 = productId, key2 = reloadProduct) {
        productViewModel.getProduct(productId.toInt())
    }

    val pagerState = rememberPagerState(pageCount = { product.value.images.size })

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
        IconButton(
            onClick = {
                navController.navigateUp()
            },
            modifier = Modifier
                .padding(top = 24.dp, start = 32.dp)
                .size(40.dp)
                .shadow(1.dp, shape = RoundedCornerShape(6.dp))
                .background(color = Color.White)
                .zIndex(99F)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color(0xff242424),
                modifier = Modifier.size(24.dp),
            )
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(start = 28.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 50.dp))
                        .align(alignment = Alignment.CenterEnd)
                ) {
                    val imgUrl = BASE_URL + product.value.images[it].imageUrl
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        var imgLoading by remember { mutableStateOf(true) }
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = null,
                            onLoading = { imgLoading = true },
                            onSuccess = { imgLoading = false },
                            onError = { imgLoading = false },
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (imgLoading) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.img_loading
                                )
                            )
                            LottieAnimation(
                                composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(bottom = 30.dp, end = 50.dp)
                        .zIndex(99f)
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        Box(
                            modifier = Modifier
                                .height(4.dp)
                                .width(
                                    if (pagerState.currentPage == iteration) 30.dp else 15.dp
                                )
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    color = if (pagerState.currentPage == iteration) Color(
                                        0xff303030
                                    ) else Color(
                                        0xffF0F0F0
                                    )
                                )
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(64.dp)
                        .height(192.dp)
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color(0xff000000),
                            clip = true,
                            shape = RoundedCornerShape(40.dp)
                        )
                        .background(Color.White)
                        .align(alignment = Alignment.CenterStart)
                        .padding(vertical = 16.dp)
                ) {
                    repeat(3) { i ->
                        val id = when (i) {
                            0 -> R.drawable.color_1
                            1 -> R.drawable.color_2
                            else -> R.drawable.color_3
                        }
                        Image(
                            painter = painterResource(id),
                            contentDescription = null,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = product.value!!.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xff303030)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "$ ${product.value.price}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        IconButton(
                            onClick = {
                                qty++
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(30.dp)
                                .background(color = Color(0xffE0E0E0))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = String.format(Locale.getDefault(), "%02d", qty),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp
                        )
                        IconButton(
                            onClick = {
                                if (qty > 1) {
                                    qty--
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(30.dp)
                                .background(color = Color(0xffE0E0E0))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = null,
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color(0xffF2C94C),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${product.value.getRating()}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff303030)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "(${product.value.getReviewCount()} reviews)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff808080)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = product.value.description,
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color(0xff606060)
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .zIndex(99f)
        ) {
            IconButton(
                onClick = {
                    reloadProduct = !reloadProduct

                    if (product.value.isFavorite == true) {
                        productViewModel.removeFavoriteProduct(product.value.id)
                    } else {
                        productViewModel.addFavoriteProduct(product.value.id)
                    }
                },
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xffF0F0F0))
            ) {
                Icon(
                    painter = painterResource(
                        id = when {
                            product.value.isFavorite == true -> R.drawable.ic_save_filled
                            else -> R.drawable.ic_save
                        }
                    ),
                    contentDescription = null,
                    tint = Color(0xff303030),
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = {
                    try {
                        cartHelper.addItem(product.value, qty)
                        cartHelper.saveCart(context)
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff242424),
                ),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add to Cart", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
