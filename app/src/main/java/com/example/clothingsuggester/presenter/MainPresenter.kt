package com.example.clothingsuggester.presenter

import com.example.clothingsuggester.api.WeatherApi
import com.example.clothingsuggester.data.ClothesData
import com.example.clothingsuggester.data.SharedPrefsManager
import com.example.clothingsuggester.view.MainView

class MainPresenter(private val sharedPrefsManager: SharedPrefsManager) {
    private val weatherApi: WeatherApi = WeatherApi()
    lateinit var view: MainView

    fun getWeatherRequest(country: String) {
        weatherApi.getWeatherData(country) { result, error ->
            if (error != null) {
                view.showNetworkError()
            } else {
                result?.let {
                    val temperature = result.current.temperature
                    val date = result.location.localtime.take(10)
                    val drawableId = getClothesImage(temperature, date)
                    view.showWeatherData(it)
                    view.showClothesImage(drawableId)
                    sharedPrefsManager.saveDate(date)
                }
            }
        }
    }

    private fun getClothesImage(temperature: Int, date: String): Int {
        val clothesData = ClothesData()
        return if (isSavedDate(date)) {
            if (sharedPrefsManager.isImageSaved()) {
                sharedPrefsManager.getSavedImage()
            } else {
                val drawableId = clothesData.getClothesImage(temperature)
                sharedPrefsManager.saveImage(drawableId)
                drawableId
            }
        } else {
            val drawableId = clothesData.getClothesImage(temperature)
            sharedPrefsManager.saveImage(drawableId)
            drawableId
        }
    }

    private fun isSavedDate(apiDate: String): Boolean {
        val savedDate = sharedPrefsManager.getSavedDate()
        return savedDate == apiDate.take(10)
    }
}
