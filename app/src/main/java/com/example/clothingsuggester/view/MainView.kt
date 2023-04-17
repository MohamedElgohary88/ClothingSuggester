package com.example.clothingsuggester.view

import com.example.clothingsuggester.response.success.WeatherData

interface MainView {
    fun setWeatherData(result: WeatherData)
    fun onFailure()

}