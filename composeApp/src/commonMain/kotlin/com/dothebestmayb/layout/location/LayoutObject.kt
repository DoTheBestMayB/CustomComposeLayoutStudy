package com.dothebestmayb.layout.location

sealed interface LayoutObject {

    val id: String

    data class LocationObject(
        override val id: String,
        val startX: Int,
        val startY: Int,
        val endX: Int,
        val endY: Int,
    ): LayoutObject

    data class ScreenObject(
        override val id: String,
        val width: Int,
    ): LayoutObject
}
