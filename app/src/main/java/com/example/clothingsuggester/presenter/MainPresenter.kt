package com.example.clothingsuggester.presenter

import android.icu.util.Calendar
import com.example.clothingsuggester.api.WeatherApi
import com.example.clothingsuggester.data.SharedPrefsManager
import com.example.clothingsuggester.view.MainView
import java.text.SimpleDateFormat
import java.util.Locale

class MainPresenter(val sharedPrefsManager: SharedPrefsManager) {
    val weatherApi: WeatherApi = WeatherApi()
    lateinit var view: MainView

    fun getWeatherRequest(country: String) {
        weatherApi.getWeatherData(country) { result, error ->
            if (error != null) {
                view.onFailure()
            } else {
                result?.let {
                    view.setWeatherData(it)
                    sharedPrefsManager.saveDate(result.location.localtime.take(10))
                }

            }
        }
    }

    fun compareDate(apiDate: String): Boolean {
        val savedDate = sharedPrefsManager.getSavedDate()
        return savedDate == apiDate.take(10)
    }

    fun getLocalDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
