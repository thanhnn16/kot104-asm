package com.ps28372.kotlin_asm.view.auth

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ps28372.kotlin_asm.R
import com.ps28372.kotlin_asm.model.UserResponse
import com.ps28372.kotlin_asm.repository.UserRepository
import com.ps28372.kotlin_asm.utils.ApiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun Login(onNavigateToRegister: () -> Unit, modifier: Modifier, onNavigateHome: () -> Unit) {
    val context = LocalContext.current
    var loginState by remember { mutableStateOf<ApiState<UserResponse>?>(null) }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    LaunchedEffect(loginState) {
        if (loginState is ApiState.Success) {
            Toast.makeText(
                context,
                "Login successfully",
                Toast.LENGTH_SHORT
            ).show()
            onNavigateHome()
        } else if (loginState is ApiState.Error) {
            Toast.makeText(
                context,
                (loginState as ApiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    var email by remember { mutableStateOf("thanhnn5.work@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun loginHandler() {
        loginState = ApiState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.login(email, password)
                Log.d("LOGIN", "Response: ${response.message}")
                Log.d("LOGIN", "Response: ${response.error}")
                loginState = if ("successfully" in response.message) {
                    with(sharedPreferences.edit()) {
                        putString("token", response.token)
                        apply()
                    }
                    ApiState.Success(response)
                } else {
                    ApiState.Error(response.error)
                }
            } catch (e: Exception) {
                loginState = ApiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    var isLoginEnabled by remember { mutableStateOf(true) }

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
            HorizontalDivider(
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
            HorizontalDivider(
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
            Text(
                text = "Hello!",
                color = Color(0xFF909090),
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ("Welcome back").uppercase(),
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
                    Text(text = "Email", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (!isValidEmail(email)) {
                                emailError = "Email is not valid"
                                isLoginEnabled = false
                            } else {
                                emailError = ""
                                isLoginEnabled = isValidPassword(password)
                            }
                        },
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Password", color = Color(0xFF909090), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if (!isValidPassword(password)) {
                                passwordError = "Password must be at least 6 characters"
                                isLoginEnabled = false
                            } else {
                                passwordError = ""
                                isLoginEnabled = isValidEmail(email)
                            }
                        },
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Forgot password?",
                        color = Color(0xFF303030),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable { })
                    Spacer(modifier = Modifier.height(32.dp))
                    ElevatedButton(
                        onClick = { loginHandler() },
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
                        enabled = isLoginEnabled,
                    ) {
                        if (loginState is ApiState.Loading) {
                            isLoginEnabled = false
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            isLoginEnabled = true
                            Text(
                                text = "Log in",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Text(
                        text = ("sign up").uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = onNavigateToRegister)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    Login(onNavigateToRegister = {}, onNavigateHome = {}, modifier = Modifier.fillMaxSize())
//}
