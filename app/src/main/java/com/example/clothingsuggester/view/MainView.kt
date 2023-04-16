package com.example.clothingsuggester.view

import com.example.clothingsuggester.model.success.WeatherData

interface MainView {
    fun setData(result: WeatherData)
    fun onFailure()
}