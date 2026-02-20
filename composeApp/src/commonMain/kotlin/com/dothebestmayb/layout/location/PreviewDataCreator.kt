package com.dothebestmayb.layout.location

private const val CELL = 2 // 좌석 1개가 2x2를 차지

private const val GAP = CELL

private fun xOf(col: Int): Int {
    return when {
        col <= 6 -> (col - 1) * CELL
        col in 7..21 -> (6 * CELL + GAP) + (col - 7) * CELL
        else -> (21 * CELL + GAP) + GAP + (col - 22) * CELL
    }
}

private fun yOf(rowChar: Char): Int {
    val rowIndex = rowChar - 'A' // A가 0, B가 1 ...
    return rowIndex * CELL
}

private fun seat(row: Char, col: Int): LayoutObject.LocationObject {
    val x1 = xOf(col)
    val y1 = yOf(row)
    return LayoutObject.LocationObject(
        id = "$row$col",
        startX = x1,
        startY = y1,
        endX = x1 + CELL,
        endY = y1 + CELL,
    )
}

// 아래 값들은 API로 받아온 정보를 바탕으로 가공되지만, 코드 축약을 위해 AI를 이용해서 하드코딩 데이터를 생성했습니다.
fun buildPreviewLocations(): List<LayoutObject.LocationObject> {
    val result = mutableListOf<LayoutObject.LocationObject>()

    run {
        val row = 'A'
        for (c in 9..21) {
            val id = "$row$c"
            result += seat(row, c)
        }
    }

    run {
        val row = 'B'
        val cols = (3..6) + (7..21) + (22..25)
        for (c in cols) {
            val id = "$row$c"
            result += seat(row, c)
        }
    }

    for (row in 'C'..'N') {
        val cols = (1..6) + (7..21) + (22..27)
        for (c in cols) {
            val id = "$row$c"
            result += seat(row, c)
        }
    }

    run {
        val row = 'O'
        val cols = (1..4) + (7..21) + (22..27)
        for (c in cols) {
            val id = "$row$c"
            result += seat(row, c)
        }
    }

    return result
}