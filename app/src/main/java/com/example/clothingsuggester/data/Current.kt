package com.example.clothingsuggester.data

data class Current(
    val temperature: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>
)
