package com.dothebestmayb.layout.location

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.dothebestmayb.Layout.core.designsystem.theme.extended
import com.dothebestmayb.layout.theme.LayoutTheme


@Composable
fun LocationLayoutBug(
    state: LocationUiState,
    modifier: Modifier = Modifier,
    itemContent: @Composable (LayoutObject) -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val minScale = 0.5f
    val maxScale = 3.0f

    val density = LocalDensity.current
    val locationSizePx = with(density) {
        20.dp.roundToPx()
    }
    val screenHeightPx = with(density) {
        150.dp.roundToPx()
    }

    val seatGridWidthPx = (state.gridWidth * locationSizePx).toFloat()
    val screenRealWidthPx = maxOf(seatGridWidthPx / 3f, (locationSizePx * 8).toFloat())

    val totalContentWidthPx = maxOf(seatGridWidthPx, screenRealWidthPx)
    val totalContentHeightPx = (state.gridHeight * locationSizePx + screenHeightPx).toFloat()

    // Q. 왜 BoxWithConstrains를 사용해야 하는지 이해하지 못했습니다😨
    BoxWithConstraints(modifier = modifier) {
        val viewportWidth = constraints.maxWidth.toFloat()
        val viewportHeight = constraints.maxHeight.toFloat()

        LaunchedEffect(totalContentWidthPx, totalContentHeightPx, viewportWidth, viewportHeight) {
            if (totalContentWidthPx < viewportWidth || totalContentHeightPx < viewportHeight) {
                val initialX =
                    if (totalContentWidthPx < viewportWidth) (viewportWidth - totalContentWidthPx) / 2f else 0f
                val initialY =
                    if (totalContentHeightPx < viewportHeight) (viewportHeight - totalContentHeightPx) / 2f else 0f
                offset = Offset(initialX, initialY)
            }
        }

        /**
         * 줌 인, 아웃, 드래그에 맞게 컨텐츠의 크기와 화면의 위치를 조정하기 위한 함수입니다.
         */
        fun clampOffset(candidate: Offset, currentScale: Float): Offset {
            // 현재 스케일이 적용된 실제 컨텐츠 크기
            val scaledContentWidth = totalContentWidthPx * currentScale
            val scaledContentHeight = totalContentHeightPx * currentScale

            // 여유 공간 (화면 크기의 20%)
            val bufferX = viewportWidth * 0.2f
            val bufferY = viewportHeight * 0.2f

            val diffX = viewportWidth - scaledContentWidth
            val diffY = viewportHeight - scaledContentHeight

            val (minX, maxX) = if (diffX > 0) {
                // 컨텐츠가 화면보다 작을 때: 중앙 정렬을 기준으로 버퍼만큼만 움직이게 함
                val centerX = diffX / 2f
                (centerX - bufferX) to (centerX + bufferX)
            } else {
                // 컨텐츠가 화면보다 클 때: 일반적인 스크롤 (오른쪽 끝 ~ 0)
                (diffX - bufferX) to (0f + bufferX)
            }

            val (minY, maxY) = if (diffY > 0) {
                val centerY = diffY / 2f
                (centerY - bufferY) to (centerY + bufferY)
            } else {
                (diffY - bufferY) to (0f + bufferY)
            }

            return Offset(
                x = candidate.x.coerceIn(minX, maxX),
                y = candidate.y.coerceIn(minY, maxY),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .background(MaterialTheme.colorScheme.extended.layoutBackground)
                // 마우스 휠 줌
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()

                            if (event.type == PointerEventType.Scroll) {
                                val change = event.changes.firstOrNull() ?: continue
                                val scrollDelta = change.scrollDelta.y
                                val zoomChange = (1f - (scrollDelta * 0.1f)).coerceIn(0.8f, 1.2f)

                                val oldScale = scale
                                val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)
                                val zoomFactor = newScale / oldScale

                                val pos = change.position

                                val newOffset = pos - (pos - offset) * zoomFactor

                                scale = newScale
                                offset = clampOffset(newOffset, newScale)

                                change.consume()
                            }
                        }
                    }
                }
                // 줌, 드래그
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, _ ->
                        val oldScale = scale
                        val newScale = (scale * zoom).coerceIn(minScale, maxScale)
                        val zoomFactor = newScale / oldScale

                        // 줌 포커스 보정 : 줌 할 때 화면 시점이 유지되도록 조정
                        val newOffset = centroid - (centroid - offset) * zoomFactor + pan

                        scale = newScale
                        offset = clampOffset(newOffset, newScale)
                    }
                }
        ) {
            Layout(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y

                        transformOrigin = TransformOrigin(0f, 0f)
                    },
                content = {
                    state.layoutObjects.forEach { location ->
                        key(location.id) {
                            itemContent(location)
                        }
                    }
                },
            ) { measurables, _ ->
                val placeables = measurables.mapIndexed { index, measurable ->
                    val item = state.layoutObjects[index]

                    val itemWidth: Int
                    val itemHeight: Int

                    when (item) {
                        is LayoutObject.LocationObject -> {
                            itemWidth = (item.endX - item.startX) * locationSizePx
                            itemHeight = (item.endY - item.startY) * locationSizePx
                        }
                        is LayoutObject.ScreenObject -> {
                            itemWidth = screenRealWidthPx.toInt()
                            itemHeight = screenHeightPx
                        }
                    }

                    measurable.measure(Constraints.fixed(itemWidth, itemHeight))
                }
                layout(totalContentWidthPx.toInt(), totalContentHeightPx.toInt()) {
                    placeables.fastForEachIndexed { index, placeable ->
                        val item = state.layoutObjects[index]

                        var x: Int
                        var y: Int

                        when (item) {
                            is LayoutObject.LocationObject -> {
                                val gridOffsetX = (totalContentWidthPx - seatGridWidthPx) / 2

                                x = item.startX * locationSizePx + gridOffsetX.toInt()
                                y = item.startY * locationSizePx + screenHeightPx
                            }
                            is LayoutObject.ScreenObject -> {
                                val itemWidth = placeable.width
                                x = ((totalContentWidthPx - itemWidth) / 2).toInt()
                                y = 0
                            }
                        }

                        placeable.place(x, y)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val locations = buildPreviewLocations()
    val screen = LayoutObject.ScreenObject(
        id = "screen",
        width = locations.size / 3
    )
    val state = LocationUiState(
        gridWidth = 58,
        gridHeight = 30,
        layoutObjects = locations + screen,
    )
    LayoutTheme {
        LocationLayout(
            state = state,
            itemContent = { layoutObject ->
                LayoutItem(
                    item = layoutObject,
                    onClick = {},
                )
            }
        )
    }
}
