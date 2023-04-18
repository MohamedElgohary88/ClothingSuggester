package com.example.clothingsuggester.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.clothingsuggester.R
import com.example.clothingsuggester.data.SharedPrefsManager
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.response.success.WeatherData
import com.example.clothingsuggester.presenter.MainPresenter
import com.example.clothingsuggester.util.Constants

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val sharedPrefsManager: SharedPrefsManager by lazy {
        SharedPrefsManager(applicationContext)
    }
    private val presenter: MainPresenter by lazy {
        MainPresenter(sharedPrefsManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.view = this
        presenter.getWeatherRequest("tanta")
    }

    override fun showWeatherData(result: WeatherData) {
        val date = result.location.localtime.take(10)
        val temperature = result.current.temperature
        runOnUiThread {
            setWeatherImage(binding.imageWeather, temperature)
            binding.textTemperature.text = " $temperature C"
            binding.textStatus.text = result.current.weather_descriptions[0]
            binding.textDate.text = date
        }
    }

    override fun showClothesImage(drawableId: Int) {
        binding.clothesImage.setImageResource(drawableId)
    }

    private fun View.setVisibility(visibility: Boolean) {
        this.visibility = if (visibility) { View.VISIBLE } else { View.GONE }
    }

    private fun setWeatherImage(lottieAnimationView: LottieAnimationView, temperature: Int) {
        when (temperature) {
            in Constants.WINTER_RANGE -> {
                lottieAnimationView.setAnimation(R.raw.rainy)
                lottieAnimationView.playAnimation()
            }
            in Constants.AUTUMN_RANGE -> {
                lottieAnimationView.setAnimation(R.raw.cloudy)
                lottieAnimationView.playAnimation()
            }
            else -> {
                lottieAnimationView.setAnimation(R.raw.sunny)
                lottieAnimationView.playAnimation()
            }
        }
    }

    override fun showNetworkError() {
        runOnUiThread {
            binding.lottieNoNetwork.setVisibility(true)
            binding.constraintTop.setVisibility( false)
            binding.constraintBottom.setVisibility(false)
            binding.imageWeather.setVisibility(false)
        }
    }
}