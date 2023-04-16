package com.example.clothingsuggester.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.clothingsuggester.R
import com.example.clothingsuggester.data.ClothesData
import com.example.clothingsuggester.data.SharedPreferences
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.model.success.WeatherData
import com.example.clothingsuggester.presenter.MainPresenter
import com.example.clothingsuggester.view.util.Constants

class MainActivity : AppCompatActivity(), MainView {
    lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter()
    private val clothesData = ClothesData()
    private val sharedPreferences = SharedPreferences(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.view = this
        presenter.getRequestUsingOkHttp("tanta")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun setData(result: WeatherData) {
        val date = result.location.localtime.take(10)
        val temperature = result.current.temperature
        runOnUiThread {
            binding.textTemperature.text = " $temperature C"
            binding.textStatus.text = result.current.weather_descriptions[0]
            binding.textTown.text = result.location.region
            binding.textDate.text = date
            setClothesImage(temperature, date)
            Log.d("Mimo", " Api -> $date ------- Locale -> ${sharedPreferences.getLocalDate()}")
            setWeatherImage(binding.imageWeather, temperature)
        }
    }

    /* @RequiresApi(Build.VERSION_CODES.O)
     private fun setClothesImage(temperature: Int, date: String) {
       if (sharedPreferences.compareDate(date)){
           sharedPreferences.getSavedImage()
       }else{
          // here add the filter not to get random image but avoid the last day image (saved image)
           clothesData.getClothesImage(temperature)
       }
     }
 */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClothesImage(temperature: Int, date: String) {
        if (!sharedPreferences.compareDate(date)) {
            val drawableId = clothesData.getClothesImage(temperature)
            binding.clothesImage.setImageResource(drawableId)
            sharedPreferences.saveImage(drawableId)
        } else {
            if (sharedPreferences.checkImageSaved()) {
                binding.clothesImage.setImageResource(clothesData.getClothesImage(temperature))
            }

            binding.clothesImage.setImageResource(sharedPreferences.getSavedImage())
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