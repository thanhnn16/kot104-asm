package com.ps28372.kotlin_asm.view.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.ps28372.kotlin_asm.R

@Composable
fun ProductDetails(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)

        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boarding_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 28.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(bottomStart = 50.dp))
                        .align(alignment = Alignment.CenterEnd)
                )
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
                ) {

                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Minimal Stand",
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
                Text(text = "$ 50", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
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
                        text = "01",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .size(30.dp)
                            .background(color = Color(0xffE0E0E0))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
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
                    text = "4.5",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff303030)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "(50 reviews)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xff808080)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Minimal Stand is made of by natural wood. The design that is very simple and minimal. This is truly one of the best furniture's in any family for now. With 3 different colors, you can easily select the best match for your home.",
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xff606060)
                )
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
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xffF0F0F0))
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xff303030),
                    modifier = Modifier.size(24.dp)
                )
            }
            Button(
                onClick = { /*TODO*/ },
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
