package com.dothebestmayb.layout.location

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.dothebestmayb.Layout.core.designsystem.theme.extended
import com.dothebestmayb.layout.theme.LayoutTheme
import kotlin.math.roundToInt


const val minScale = 0.5f
const val maxScale = 3.0f

val seatSizeDp = 20.dp
val screenHeightDp = 150.dp

val defaultLocationColor = Color.White

@Composable
fun LocationLayout(
    state: LocationUiState,
    modifier: Modifier = Modifier,
    itemContent: @Composable (LayoutObject) -> Unit,
) {
    // 줌 인, 아웃시 사용되는 확대 배율 값
    var scale by remember { mutableFloatStateOf(1f) }
    // 전체 화면에서 사용자에게 보여줄 영역의 위치를 설정하는 값
    var offset by remember { mutableStateOf(Offset.Zero) }

    val density = LocalDensity.current
    val locationSizePx = with(density) { seatSizeDp.roundToPx() }
    val screenHeightPx = with(density) { screenHeightDp.roundToPx() }

    // 좌상단이 (0, 0)인 좌표계로 되어 있으며, gridWidth, gridHeight은 Location이 포함된 전체 길이를 나타냅니다.
    val gridWidthPxOnly = state.gridWidth * locationSizePx
    val gridHeightPxOnly = state.gridHeight * locationSizePx + screenHeightPx

    // 좌석 너비의 10% 만큼 상, 하, 좌, 우에 여백을 추가했습니다.
    val paddingXPx = (gridWidthPxOnly * 0.1f).roundToInt()
    val paddingYPx = (gridHeightPxOnly * 0.1f).roundToInt()

    // 여백을 포함한 전체 영역
    val paddedGridWidthPxOnly = gridWidthPxOnly + paddingXPx * 2
    val paddedGridHeightPxOnly = gridHeightPxOnly + paddingYPx * 2

    val gridContentWidthPx = paddedGridWidthPxOnly
    val gridContentHeightPx = paddedGridHeightPxOnly + screenHeightPx

    val contentWidthDp = with(density) { gridContentWidthPx.toDp() }
    val contentHeightDp = with(density) { gridContentHeightPx.toDp() }

    val contentBounds = Rect(0f, 0f, gridContentWidthPx.toFloat(), gridContentHeightPx.toFloat())

    var didInit by rememberSaveable(state.gridWidth, state.gridHeight) { mutableStateOf(false) }
    var viewportSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(contentBounds, viewportSize, scale) {
        if (viewportSize == IntSize.Zero) return@LaunchedEffect

        val visibleW = viewportSize.width.toFloat() / scale
        val visibleH = viewportSize.height.toFloat() / scale

        val minX = minOf(contentBounds.left, contentBounds.right - visibleW)
        val maxX = maxOf(contentBounds.left, contentBounds.right - visibleW)
        val minY = minOf(contentBounds.top, contentBounds.bottom - visibleH)
        val maxY = maxOf(contentBounds.top, contentBounds.bottom - visibleH)

        if (!didInit) {
            // 초기에는 화면의 정중앙을 가리키도록 x 좌표를 조절합니다.
            val centeredX =
                ((contentBounds.right - contentBounds.left) - visibleW) / 2f + contentBounds.left
            offset = Offset(
                centeredX.coerceIn(minX, maxX),
                offset.y.coerceIn(minY, maxY)
            )
            didInit = true
        } else {
            offset = Offset(
                offset.x.coerceIn(minX, maxX),
                offset.y.coerceIn(minY, maxY)
            )
        }
    }

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            // clipToBounds를 붙여 이 Composable에게 할당된 영역 내에서만 그려지도록 처리했습니다.
            .clipToBounds()
            .background(MaterialTheme.colorScheme.extended.layoutBackground)
            .onSizeChanged { viewportSize = it }
            // web 환경에서 마우스 스크롤에 따라 줌 인, 아웃을 구현하기 위한 코드입니다.
            // Scroll을 처리하는 Modifier.onPointerEvent가 있지만, commonMain에서는 사용할 수 없기에 pointerInput으로 처리했습니다.
            // https://kotlinlang.org/docs/multiplatform/compose-desktop-mouse-events.html#scroll-listeners
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type != PointerEventType.Scroll) continue

                        val change = event.changes.firstOrNull() ?: continue
                        val scrollDelta = change.scrollDelta.y
                        val zoomChange = if (scrollDelta > 0) {
                            0.9f
                        } else if (scrollDelta < 0) {
                            1.1f
                        } else {
                            1f
                        }
                        val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)

                        offset = offset.calculateNewOffset(
                            centroid = change.position,
                            pan = Offset.Zero,
                            oldScale = scale,
                            newScale = newScale,
                            viewportSize = size,
                            contentBounds = contentBounds
                        )

                        scale = newScale
                        change.consume()
                    }
                }
            }
            // 줌, 드래그를 처리하는 코드입니다.
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val newScale = (scale * zoom).coerceIn(minScale, maxScale)

                    offset = offset.calculateNewOffset(
                        centroid = centroid,
                        pan = pan,
                        oldScale = scale,
                        newScale = newScale,
                        viewportSize = size,
                        contentBounds = contentBounds
                    )

                    scale = newScale
                }
            }
    ) {
        /**
         * Layout을 총 2개 사용했습니다.
         * 안쪽 Layout이 부모 Composable의 width, height 제약 조건과 무관하게 동작하도록 바깥 Layout의 width, height을 Infinity로 설정합니다.
         * 이렇게 함으로써 내부 Layout은 부모 Composable에 영향을 받지 않고 (0, 0) 좌표 기반으로 각 Content를 정확하게 배치할 수 있다고 합니다.(AI가)
         * Layout을 두 번 중첩하지 않고 문제를 해결할 수 있는 방법이 있는지 여러 세션을 통해 확인했지만, 이 방법 외에는 모두 실패했습니다.
         *
         * LazyLayout을 사용하면, 각 content가 보여지는지 줌, 드래그를 할 때마다 계산해야 합니다.(Layout phase, measure and place)
         * 이것은 매우 비효율적이라고 생각했고, Location은 많아봐야 1000개도 안 되기 때문에 보이지 않는 것도 그리는 것이 더 성능적 이점이 있다고 판단했습니다.
         * -> 면접에서 이렇게 답변하면 benchmark를 수행해봤는지 물어볼 수 있겠으나, 어떻게 측정해야 하는지 모르겠습니다.
         * 현재 코드는 Layout phase를 한 번만 수행하고, draw phase에서(graphicsLayer 람다 블록) 줌, 드래그를 처리합니다.
         */
        Layout(
            modifier = Modifier.fillMaxSize(),
            content = {
                Layout(
                    modifier = Modifier
                        // requiredSize를 이용해서 필요한 width, height을 가지도록 강제했습니다.
                        // 그런데 Layout을 두 번 중첩함으로써 이 Layout에게 전달된 width, height의 MAX는 INFINITY이기 때문에 불필요할지도 모르겠습니다.
                        // Q. 이 코드 아직도 필요한지 AI에게 물어봤으나, 명확한 근거와 답변을 받지 못했습니다.( ~~ 때문에 없애면 문제 생기는 거 아니야? 라고 물어보면 그럴 수도 있습니다. 따라서 없애지 않는 것이 좋습니다. 라고 불확실하게 답변합니다.)
                        //  지훈 님이 생각하시기에 requiredSize가 필요하다고 보시나요?
                        .requiredSize(contentWidthDp, contentHeightDp)
                        // Q. 버그가 발생하면 문제를 해결하기 위해 graphicsLayer를 이해하고자 했으나, 어떤 것을 이해해야 하고, 어떤 자료를 보고 이해해야 하는지 모르겠습니다.
                        //  transformOrigin을 왜 (0, 0)으로 설정해야 하고, translationX, Y를 설정할 때 왜 "-offset * scale"로 설정해야 하는지 이해하지 못했습니다.
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = -offset.x * scale
                            translationY = -offset.y * scale
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
                                val ratioWidth = item.width * locationSizePx
                                val minWidth = locationSizePx * 8
                                itemWidth = maxOf(ratioWidth, minWidth)
                                itemHeight = screenHeightPx
                            }
                        }

                        measurable.measure(Constraints.fixed(itemWidth, itemHeight))
                    }

                    layout(gridContentWidthPx, gridContentHeightPx) {
                        placeables.fastForEachIndexed { index, placeable ->
                            val item = state.layoutObjects[index]

                            val x: Int
                            val y: Int

                            when (item) {
                                is LayoutObject.LocationObject -> {
                                    x = item.startX * locationSizePx + paddingXPx
                                    y = item.startY * locationSizePx + screenHeightPx + paddingYPx
                                }
                                is LayoutObject.ScreenObject -> {
                                    x = (gridContentWidthPx - placeable.width) / 2
                                    y = 0
                                }
                            }

                            placeable.place(x, y)
                        }
                    }
                }
            }
        ) { measurables, constraints ->
            val contentRoot = measurables.first().measure(
                Constraints(
                    minWidth = 0,
                    minHeight = 0,
                    maxWidth = Constraints.Infinity,
                    maxHeight = Constraints.Infinity
                )
            )
            layout(constraints.maxWidth, constraints.maxHeight) {
                contentRoot.place(0, 0)
            }
        }
    }
}

/**
 * 줌 인, 아웃, 드래그에 따라 사용자에게 보여줄 화면의 위치와 각 컨텐츠의 크기를 계산하는 함수입니다.
 */
private fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    oldScale: Float,
    newScale: Float,
    viewportSize: IntSize,
    contentBounds: Rect
): Offset {
    // Google 공식 영상에 사용된 공식을 사용했습니다.
    // https://www.youtube.com/watch?v=1tkVjBxdGrk
    // https://gist.github.com/JolandaVerhoef/41bbacadead2ba3ce8014d67014efbdd#file-compose-photogrid-kt-L394
    val unbounded = (this + centroid / oldScale) - (centroid / newScale + pan / oldScale)

    val visibleW = viewportSize.width.toFloat() / newScale
    val visibleH = viewportSize.height.toFloat() / newScale

    val minX = minOf(contentBounds.left, contentBounds.right - visibleW)
    val maxX = maxOf(contentBounds.left, contentBounds.right - visibleW)
    val minY = minOf(contentBounds.top, contentBounds.bottom - visibleH)
    val maxY = maxOf(contentBounds.top, contentBounds.bottom - visibleH)

    return Offset(
        x = unbounded.x.coerceIn(minX, maxX),
        y = unbounded.y.coerceIn(minY, maxY)
    )
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
