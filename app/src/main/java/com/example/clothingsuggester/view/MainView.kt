package com.example.clothingsuggester.view

import androidx.annotation.DrawableRes
import com.example.clothingsuggester.response.success.WeatherData

interface MainView {
    fun showWeatherData(result: WeatherData)
    fun showNetworkError()
    fun showClothesImage(@DrawableRes drawableId: Int)
}