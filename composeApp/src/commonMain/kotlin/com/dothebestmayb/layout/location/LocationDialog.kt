package com.dothebestmayb.layout.location

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import layout.composeapp.generated.resources.Res
import layout.composeapp.generated.resources.close_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun LocationDialog(
    isBug: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // 아래 값들은 API로 받아온 정보를 바탕으로 가공되지만, 코드 축약을 위해 하드코딩했습니다.
    val locationState = remember {
        val locations = buildPreviewLocations()
        val screen = LayoutObject.ScreenObject(
            id = "screen",
            width = 58 / 3
        )
        LocationUiState(
            gridWidth = 58,
            gridHeight = 30,
            layoutObjects = locations + screen,
        )
    }

    Dialog(
        onDismissRequest = onClose,
    ) {
        Card(
            modifier = modifier
                .widthIn(max = 1200.dp)
                .heightIn(min = 500.dp, max = 800.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Location",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    IconButton(
                        onClick = onClose,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.close_24px),
                            contentDescription = "닫기",
                        )
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                )

                if (isBug) {
                    LocationLayoutBug(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1f),
                        state = locationState,
                        itemContent = { layoutObject ->
                            LayoutItem(
                                item = layoutObject,
                                onClick = {},
                            )
                        }
                    )
                } else {
                    LocationLayout(
                        modifier = Modifier.fillMaxWidth()
                            .weight(1f),
                        state = locationState,
                        itemContent = { layoutObject ->
                            LayoutItem(
                                item = layoutObject,
                                onClick = {},
                            )
                        }
                    )
                }
            }
        }
    }
}