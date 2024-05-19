package com.ps28372.kotlin_asm.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps28372.kotlin_asm.R

@Composable
fun Register(onNavigateToLogin: () -> Unit, modifier: Modifier) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRePasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
            .padding(top = 24.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .padding(start = 30.dp, end = 18.dp),
                color = Color(0xFFBDBDBD)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                Modifier.size(64.dp)
            )
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .padding(start = 18.dp, end = 30.dp),
                color = Color(0xFFBDBDBD)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.padding(horizontal = 30.dp),
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Welcome".uppercase(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(16.dp),
            shape = RectangleShape,
        ) {
            Column(modifier = Modifier.padding(top = 35.dp, bottom = 40.dp)) {
                Column(modifier = Modifier.padding(start = 30.dp)) {
                    Text(text = "Name", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp, bottom = 4.dp)
                            ) {
                                innerTextField()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .drawBehind {
                                drawLine(
                                    color = Color(0xFFBDBDBD),
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Email", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp, bottom = 4.dp)
                            ) {
                                innerTextField()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .drawBehind {
                                drawLine(
                                    color = Color(0xFFBDBDBD),
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Password", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .drawBehind {
                                drawLine(
                                    color = Color(0xFFBDBDBD),
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f,
                                )
                            },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp, bottom = 4.dp)
                            ) {
                                innerTextField()
                                IconButton(
                                    onClick = { isPasswordVisible = !isPasswordVisible },
                                    modifier = Modifier.size(20.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = Color(0xFFBDBDBD)
                                    ),
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_eye),
                                            contentDescription = "Toggle password visibility",
                                        )
                                    },
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "Confirm Password", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = rePassword,
                        onValueChange = { rePassword = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .drawBehind {
                                drawLine(
                                    color = Color(0xFFBDBDBD),
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f,
                                )
                            },
                        visualTransformation = if (isRePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 24.dp, bottom = 4.dp)
                            ) {
                                innerTextField()
                                IconButton(
                                    onClick = { isRePasswordVisible = !isRePasswordVisible },
                                    modifier = Modifier.size(20.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = Color(0xFFBDBDBD)
                                    ),
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_eye),
                                            contentDescription = "Toggle re-password visibility",
                                        )
                                    },
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF303030),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 3.dp,
                            pressedElevation = 5.dp,
                        ),
                    ) {
                        Text(
                            text = "sign up".uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Row {
                        Text(text = "Already have an account? ",
                            color = Color(0xFF808080),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold)
                        Text(
                            text = ("sign in").uppercase(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable(onClick = onNavigateToLogin)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Register(onNavigateToLogin = {}, modifier = Modifier)
}
