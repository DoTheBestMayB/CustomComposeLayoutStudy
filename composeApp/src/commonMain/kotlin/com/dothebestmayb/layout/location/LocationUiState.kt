package com.dothebestmayb.layout.location

data class LocationUiState(
    val gridWidth: Int = 0,
    val gridHeight: Int = 0,
    val layoutObjects: List<LayoutObject> = emptyList(),
)
