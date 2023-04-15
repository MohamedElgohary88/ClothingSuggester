package com.example.clothingsuggester.view

import android.widget.ImageView
import com.example.clothingsuggester.model.success.WeatherData

interface MainView {
   fun setData(result: WeatherData)
}