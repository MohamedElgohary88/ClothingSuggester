package com.example.clothingsuggester.response.success

data class Current(
    val temperature: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>
)
