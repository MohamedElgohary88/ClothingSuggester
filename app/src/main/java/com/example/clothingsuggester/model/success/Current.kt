package com.example.clothingsuggester.model.success

data class Current(
    val temperature: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>
)
