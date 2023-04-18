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
import com.example.clothingsuggester.util.Constants.AUTUMN_MAX_TEMP
import com.example.clothingsuggester.util.Constants.AUTUMN_MIN_TEMP
import com.example.clothingsuggester.util.Constants.WINTER_MAX_TEMP

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

    override fun setWeatherData(result: WeatherData) {
        val date = result.location.localtime.take(10)
        val temperature = result.current.temperature
        runOnUiThread {
            setWeatherImage(binding.imageWeather, temperature)
            binding.textTemperature.text = " $temperature C"
            binding.textStatus.text = result.current.weather_descriptions[0]
            binding.textDate.text = date
        }
    }

    override fun setClothesImage(drawableId: Int) {
        binding.clothesImage.setImageResource(drawableId)
    }

    private fun setVisibility(view: View, visibility: Boolean) {
        view.visibility = if (visibility) { View.VISIBLE } else { View.INVISIBLE }
    }

    private fun setWeatherImage(lottieAnimationView: LottieAnimationView, temperature: Int) {
        when (temperature) {
            in Int.MIN_VALUE..WINTER_MAX_TEMP -> {
                lottieAnimationView.setAnimation(R.raw.rainy)
                lottieAnimationView.playAnimation()
            }
            in AUTUMN_MIN_TEMP..AUTUMN_MAX_TEMP -> {
                lottieAnimationView.setAnimation(R.raw.cloudy)
                lottieAnimationView.playAnimation()
            }
            else -> {
                lottieAnimationView.setAnimation(R.raw.sunny)
                lottieAnimationView.playAnimation()
            }
        }
    }

    override fun showFailureState() {
        runOnUiThread {
            setVisibility(binding.lottieNoNetwork, true)
            setVisibility(binding.constraintTop, false)
            setVisibility(binding.constraintBottom, false)
            setVisibility(binding.imageWeather, false)
        }
    }
}