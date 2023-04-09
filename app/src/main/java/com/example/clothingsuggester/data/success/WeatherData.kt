package com.example.clothingsuggester.data.success

import com.example.clothingsuggester.data.success.Current
import com.example.clothingsuggester.data.success.Location
import com.example.clothingsuggester.data.success.Request

data class WeatherData(
    val request: Request,
    val location: Location,
    val current: Current
)

