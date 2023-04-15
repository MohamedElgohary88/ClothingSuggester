package com.example.clothingsuggester.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.clothingsuggester.R
import com.example.clothingsuggester.model.success.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.data.ClothesData
import com.example.clothingsuggester.presenter.MainPresenter
import com.example.clothingsuggester.view.util.Constants

class MainActivity : AppCompatActivity(), MainView {
    lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter()
    private val clothesData = ClothesData()
    val LOG_TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.view = this
        setUp()
    }

    private fun setUp() {
        binding.get.setOnClickListener {
            presenter.getRequestUsingOkHttp(binding.country.query.toString())
        }
    }

    private fun getClothesImage(temperature: Int): Int {
        return when (temperature) {
            in -40..15 -> clothesData.winterClothes.random()
            in 15..25 -> clothesData.autumnClothes.random()
            else -> clothesData.summerClothes.random()
        }
    }

    private fun setClothesImage(temperature: Int, date: String) {
        if (compareDate(date)) {
            if (!isSameImageDisplayedToday(temperature)) {
                val drawableId = getClothesImage(temperature)
                binding.clothesImage.setImageResource(drawableId)
                saveImage(temperature)
            } else {
                setClothesImage(temperature, date)
            }
        } else {
            binding.clothesImage.setImageResource(getSavedImageDrawableId())
        }

    }

    private fun isSameImageDisplayedToday(temperature: Int): Boolean {
        val savedDrawableId = getSavedImageDrawableId()
        val currentDrawableId = getClothesImage(temperature)
        return savedDrawableId == currentDrawableId
    }

    private fun saveImage(temperature: Int) {
        val drawableId = getClothesImage(temperature)
        val saveShared =
            this@MainActivity.getSharedPreferences(Constants.SHARED_IMAGE, Context.MODE_PRIVATE)
        val editor = saveShared.edit()
        editor.putInt(Constants.KEY_IMAGE, drawableId)
        editor.apply()
    }

    private fun getSavedImageDrawableId(): Int {
        val getShared =
            this@MainActivity.getSharedPreferences(Constants.SHARED_IMAGE, Context.MODE_PRIVATE)
        return getShared.getInt(Constants.KEY_IMAGE, R.drawable.jacket)
    }

    private fun compareDate(date: String): Boolean {
        val savedDate = getSavedDate()
        return savedDate == date
    }

    private fun saveDate(Date: String?) {
        val saveShared =
            this@MainActivity.getSharedPreferences(Constants.SHARED_DATE, Context.MODE_PRIVATE)
        val editor = saveShared.edit()
        editor.putString(Constants.KEY_DATE, Date)
        editor.apply()
    }

    private fun getSavedDate(): String? {
        val getShared =
            this@MainActivity.getSharedPreferences(Constants.SHARED_DATE, Context.MODE_PRIVATE)
        return getShared.getString(Constants.KEY_DATE, Constants.SHARED_DATE)
    }


    @SuppressLint("SetTextI18n")
    override fun setData(result: WeatherData) {
        val date = result.location.localtime.take(10)
        val temperature = result.current.temperature
        runOnUiThread {
            binding.textTemperature.text = " $temperature C"
            binding.textStatus.text = result.current.weather_descriptions[0]
            binding.textCountry.text = result.location.name
            binding.textTown.text = result.location.region
            binding.textDate.text = date
            saveDate(date).toString()
            setWeatherIcons(result.current.weather_icons, binding.imageView)
            setClothesImage(temperature, date)
            // getClothesImage(result.current.temperature)
        }
    }

    private fun setWeatherIcons(imageUrl: List<String>, imageView: ImageView) {
        Glide.with(imageView.context).load(imageUrl[0]).placeholder(R.drawable.ic_download)
            .into(imageView)
    }
}