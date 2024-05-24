package com.ps28372.kotlin_asm.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps28372.kotlin_asm.R

@Composable
fun Onboarding(onNavigateToLogin: () -> Unit, modifier: Modifier) {
    LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.boarding_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = ("Make your").uppercase(),
            color = Color(0xFF606060),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = ("home beautiful").uppercase(),
            color = Color(0xFF303030),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "The best simple place where you discover most wonderful furniture's and make your home beautiful",
            fontSize = 18.sp,
            lineHeight = 35.sp,
            color = Color(0xFF808080),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .offset(y = (-130).dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        ElevatedButton(
            onClick = {
                onNavigateToLogin()
            },
            modifier = Modifier
                .height(54.dp)
                .padding(horizontal = 30.dp)
                .background(Color(0xFF242424)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF242424),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Get Started", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
