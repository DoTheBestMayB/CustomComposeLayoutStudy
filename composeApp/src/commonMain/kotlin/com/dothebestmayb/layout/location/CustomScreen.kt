package com.dothebestmayb.layout.location

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SCREEN",
            style = TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        )

        Canvas(modifier = Modifier.fillMaxWidth().height(60.dp)) {
            val screenWidth = size.width
            val screenHeight = size.height

            val baseColor = Color(0xFFD65012)

            val glowPath = Path().apply {
                moveTo(0f, screenHeight)
                quadraticTo(
                    x1 = screenWidth / 2,
                    y1 = -20.dp.toPx(),
                    x2 = screenWidth,
                    y2 = screenHeight
                )
                close()
            }

            drawPath(
                path = glowPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.6f),
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = screenHeight
                )
            )

            val rimPath = Path().apply {
                // 시작점 (왼쪽 아래)
                moveTo(0f, screenHeight)

                // 위쪽 곡선 (더 높게 솟음 -> 두께의 윗면)
                quadraticTo(
                    x1 = screenWidth / 2,
                    y1 = -25.dp.toPx(), // 제어점을 더 위로
                    x2 = screenWidth,
                    y2 = screenHeight
                )

                // 아래쪽 곡선 (약간 낮게 솟음 -> 두께의 아랫면)
                // 되돌아오는 곡선을 그려서 '초승달' 모양을 만듦
                quadraticTo(
                    x1 = screenWidth / 2,
                    y1 = -18.dp.toPx(), // 제어점을 약간 낮게 -> 이 차이가 두께가 됨
                    x2 = 0f,
                    y2 = screenHeight
                )
                close()
            }

            drawPath(
                path = rimPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.3f),
                        baseColor,
                        baseColor.copy(alpha = 0.3f)
                    )
                )
            )
        }
    }
}