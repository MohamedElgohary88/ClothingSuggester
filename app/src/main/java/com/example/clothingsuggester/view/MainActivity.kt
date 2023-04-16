package com.example.clothingsuggester.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.clothingsuggester.R
import com.example.clothingsuggester.data.ClothesData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.model.success.WeatherData
import com.example.clothingsuggester.presenter.MainPresenter
import com.example.clothingsuggester.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), MainView {

    lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter()
    private val clothesData = ClothesData()

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
            Log.d("Mimo", " Api -> $date ------- Locale -> ${getLocalDate()}")
            setWeatherImage(binding.imageWeather, temperature)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClothesImage(temperature: Int, date: String) {
        if (compareDate(date)) {
            if (checkImageSaved()) {
                val drawableId = clothesData.getClothesImage(temperature)
                binding.clothesImage.setImageResource(drawableId)
                saveImage(drawableId)
            } else {
                binding.clothesImage.setImageResource(getSavedImage())
            }
        } else {
            val drawableId = clothesData.getClothesImage(temperature)
            binding.clothesImage.setImageResource(drawableId)
            saveImage(drawableId)
        }
    }

    private fun saveImage(temperature: Int) {
        val saveShared =
            applicationContext.getSharedPreferences(Constants.MY_SHARED, Context.MODE_PRIVATE)
        val editor = saveShared.edit()
        val drawableId = clothesData.getClothesImage(temperature)
        editor.putInt(Constants.KEY_IMAGE, drawableId)
        editor.apply()
    }

    private fun getSavedImage(): Int {
        val getShared =
            applicationContext.getSharedPreferences(Constants.MY_SHARED, Context.MODE_PRIVATE)
        return getShared.getInt(Constants.KEY_IMAGE, 0)
    }

    private fun checkImageSaved(): Boolean {
        val sharedPreferences =
            applicationContext.getSharedPreferences(Constants.MY_SHARED, Context.MODE_PRIVATE)
        if (sharedPreferences.all.isEmpty()) {
            return true
        } else return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun compareDate(apiDate: String): Boolean {
        val localeDate = getLocalDate()
        return localeDate == apiDate.take(10)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLocalDate(): String {
        val currentDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(dateFormatter)
    }

    override fun onFailure() {
        setVisibility(binding.lottieNoNetwork, true)
        setVisibility(binding.constraintTop, false)
        setVisibility(binding.constraintBottom, false)
        setVisibility(binding.imageWeather, false)
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