package com.ps28372.kotlin_asm.view.products

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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.utils.CartHelper

@Composable
fun Cart(navController: NavHostController) {
    val context = LocalContext.current
    val cartHelper = CartHelper(context)
    var cartItems by remember { mutableStateOf(cartHelper.getItems()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = "My cart",
                    fontSize = 18.sp,
                    color = Color(0XFF303030),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your cart is empty",
                        fontSize = 24.sp,
                        color = Color(0xff808080),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 220.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                items(cartItems.size) {
                    val cartItem = cartItems[it]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            var imgLoading by remember { mutableStateOf(true) }
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            ) {
                                AsyncImage(
                                    model = cartItem.product.getFirstImageUrl(),
                                    contentDescription = null,
                                    onLoading = { imgLoading = true },
                                    onSuccess = { imgLoading = false },
                                    onError = { imgLoading = false },
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                if (imgLoading) {
                                    val composition by rememberLottieComposition(
                                        LottieCompositionSpec.RawRes(R.raw.img_loading)
                                    )
                                    LottieAnimation(
                                        composition,
                                        iterations = LottieConstants.IterateForever,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.9f),
                            ) {
                                Column {
                                    Text(
                                        text = cartItem.product.name,
                                        fontSize = 14.sp,
                                        color = Color(0xff606060),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "$ ${cartItem.product.price}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                ) {
                                    IconButton(
                                        onClick = {
                                            cartHelper.updateQuantity(
                                                cartItem.product,
                                                cartItem.quantity + 1,
                                                context
                                            )
                                            cartHelper.saveCart(context)
                                            cartItems = cartHelper.getItems()
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
                                        text = cartItem.quantity.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                    IconButton(
                                        onClick = {
                                            if (cartItem.quantity > 1) {
                                                cartHelper.updateQuantity(
                                                    cartItem.product,
                                                    cartItem.quantity - 1,
                                                    context
                                                )
                                                cartHelper.saveCart(context)
                                                cartItems = cartHelper.getItems()
                                            } else {
                                                cartHelper.removeItem(cartItem.product, context)
                                                cartHelper.saveCart(context)
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Removed from cart",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                cartItems = cartHelper.getItems()
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
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        cartHelper.removeItem(cartItem.product, context)
                                        cartHelper.saveCart(context)
                                        Toast
                                            .makeText(
                                                context,
                                                "Removed from cart",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        cartItems = cartHelper.getItems()
                                    }
                            ) {
                                Image(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = "Remove from cart",
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.Center)
                                )
                            }
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
        if (cartItems.isNotEmpty())
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp)
                    .zIndex(99f),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Transparent)
                        .padding(horizontal = 20.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                            .shadow(
                                elevation = 2.dp,
                                clip = true,
                                shape = RoundedCornerShape(10.dp),
                                spotColor = Color(0xff000000),
                                ambientColor = Color(0xff999999),
                            ),
                        value = "", onValueChange = {},
                        placeholder = {
                            Text(
                                text = "Enter your promo code",
                                fontSize = 12.sp,
                                color = Color(0xff999999),
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            cursorColor = Color(0xff999999),
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterEnd)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xff303030))
                            .zIndex(99f)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .align(alignment = Alignment.Center),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Total:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff808080)
                    )
                    Text(
                        text = "$ ${cartHelper.getTotal()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff303030)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Check out successful, total: $${cartHelper.getTotal()}",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff242424),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .zIndex(99f)
                ) {
                    Text(text = "Check out", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
    }
}
