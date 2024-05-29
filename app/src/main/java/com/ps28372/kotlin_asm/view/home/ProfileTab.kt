package com.ps28372.kotlin_asm.view.home

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProfileTab(onLogout: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {
                with(sharedPreferences.edit()) {
                    remove("token")
                    apply()
                }
                onLogout()
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Logout")
        }
    }
}
