package com.example.clothingsuggester.response.success

data class WeatherData(
    val request: Request,
    val location: Location,
    val current: Current
)

