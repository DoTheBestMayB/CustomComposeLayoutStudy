package com.dothebestmayb.layout.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LayoutItem(
    item: LayoutObject,
    onClick: (LayoutObject) -> Unit = {},
) {
    when (item) {
        is LayoutObject.ScreenObject -> {
            CustomScreen()
        }

        is LayoutObject.LocationObject -> {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxSize()
                    .clickable {
                        onClick(item)
                    }
                    .background(
                        defaultLocationColor,
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = item.id,
                )
            }
        }
    }
}