package com.dothebestmayb.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dothebestmayb.layout.location.LocationDialog
import com.dothebestmayb.layout.theme.LayoutTheme

@Composable
@Preview
fun App() {
    LayoutTheme {
        var showContent by remember { mutableStateOf(false) }
        var showBugContent by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    showContent = true
                }
            ) {
                Text(
                    text = "잘 동작하는 LocationLayout 열기",
                )
            }
            Button(
                onClick = {
                    showBugContent = true
                }
            ) {
                Text(
                    text = "잘 동작하지 않는 LocationLayoutBug 열기",
                )
            }
        }
        if (showContent) {
            LocationDialog(
                isBug = false,
                onClose = {
                    showContent = false
                }
            )
        }
        if (showBugContent) {
            LocationDialog(
                isBug = true,
                onClose = {
                    showBugContent = false
                }
            )
        }
    }
}