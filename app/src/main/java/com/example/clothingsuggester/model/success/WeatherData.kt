package com.example.clothingsuggester.model.success

data class WeatherData(
    val request: Request,
    val location: Location,
    val current: Current
)

