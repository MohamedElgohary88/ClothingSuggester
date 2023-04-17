package com.example.clothingsuggester.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.clothingsuggester.R
import com.example.clothingsuggester.data.ClothesData
import com.example.clothingsuggester.data.SharedPrefsManager
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.response.success.WeatherData
import com.example.clothingsuggester.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter()
    private val clothesData = ClothesData()
    private lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.view = this
        presenter.getWeatherRequest("tanta")
        sharedPrefsManager = SharedPrefsManager(applicationContext)
    }

    override fun setWeatherData(result: WeatherData) {
        val date = result.location.localtime.take(10)
        val temperature = result.current.temperature
        runOnUiThread {
            binding.textTemperature.text = " $temperature C"
            binding.textStatus.text = result.current.weather_descriptions[0]
            binding.textTown.text = result.location.region
            binding.textDate.text = date
            setClothesImage(temperature, date)
            Log.d("Mimo", " Api -> $date ------- Locale -> ${presenter.getLocalDate()}")
            setWeatherImage(binding.imageWeather, temperature)
        }
    }

    private fun setClothesImage(temperature: Int, date: String) {
        if (presenter.compareDate(date)) {
            if (sharedPrefsManager.isImageSaved()) {
                val drawableId = clothesData.getClothesImage(temperature)
                binding.clothesImage.setImageResource(drawableId)
                sharedPrefsManager.saveImage(drawableId)
            } else {
                binding.clothesImage.setImageResource(sharedPrefsManager.getSavedImage())
            }
        } else {
            val drawableId = clothesData.getClothesImage(temperature)
            binding.clothesImage.setImageResource(drawableId)
            sharedPrefsManager.saveImage(drawableId)
        }
    }

    override fun onFailure() {
        runOnUiThread {
            setVisibility(binding.lottieNoNetwork, true)
            setVisibility(binding.constraintTop, false)
            setVisibility(binding.constraintBottom, false)
            setVisibility(binding.imageWeather, false)
        }
    }

    private fun setVisibility(view: View, visibility: Boolean) {
        view.visibility = if (visibility) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun setWeatherImage(lottieAnimationView: LottieAnimationView, temperature: Int) {
        when (temperature) {
            in -40..15 -> {
                lottieAnimationView.setAnimation(R.raw.rainy)
                lottieAnimationView.playAnimation()
            }

            in 15..25 -> {
                lottieAnimationView.setAnimation(R.raw.cloudy)
                lottieAnimationView.playAnimation()
            }

            else -> {
                lottieAnimationView.setAnimation(R.raw.sunny)
                lottieAnimationView.playAnimation()
            }
        }
    }
}