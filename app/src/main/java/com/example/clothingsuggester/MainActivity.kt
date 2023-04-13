package com.example.clothingsuggester

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.success.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.util.Constants
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val client = OkHttpClient.Builder().build()
    val LOG_TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.get.setOnClickListener {
            try {
                if (binding.country.query.isEmpty()) {
                    Toast.makeText(this, "Please enter a correct name", Toast.LENGTH_SHORT).show()
                } else {
                    getRequestUsingOkHttp(binding.country.query.toString())
                }
            } catch (e: Exception) {
                Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun getRequestUsingOkHttp(country: String) {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegment("current")
            .addQueryParameter("access_key", "262cdf4d8acfb7476a3849b41c90b3bc")
            .addQueryParameter("query", country)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    binding.textTemperature.text = e.message.toString()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    response.body?.string()?.let { jsonString ->
                        val result = Gson().fromJson(jsonString, WeatherData::class.java)
                        runOnUiThread {
                            binding.textTemperature.text = result.current.temperature.toString()
                            binding.textStatus.text = result.current.weather_descriptions[0]
                            binding.textCountry.text = result.location.name
                            binding.textTown.text = result.location.region
                            setWeatherIcons(result.current.weather_icons, binding.imageView)
                            setClothesImage(result.current.temperature)
                            // getClothesImage(result.current.temperature)
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        binding.textTemperature.text = e.message
                    }
                }
            }
        })
    }
    fun getClothesImage(temperature: Int): Int {
        return when (temperature) {
            in -20..15 -> R.drawable.jacket
            in 15..25 -> R.drawable.sweat
            in 25..50 -> R.drawable.shirt
            else -> R.drawable.jacket // default value
        }
    }
    fun setClothesImage(temperature: Int) {
        if (isSameImageDisplayedToday(temperature)) {
            binding.clothesImage.setImageResource(R.drawable.empty)
        } else {
            val drawableId = getClothesImage(temperature)
            binding.clothesImage.setImageResource(drawableId)
            saveImage(temperature)
        }
    }
    private fun saveImage(temperature: Int) {
        val drawableId = getClothesImage(temperature).toString()
        val saveShared = this@MainActivity.getSharedPreferences(Constants.SHARED_NAME, Context.MODE_PRIVATE)
        val editor = saveShared.edit()
        editor.putString(Constants.KEY_IMAGE, drawableId)
        editor.apply()
    }
    private fun getSavedImageDrawableId(): Int {
        val getShared = this@MainActivity.getSharedPreferences(Constants.SHARED_NAME, Context.MODE_PRIVATE)
        val drawableId = getShared.getString(Constants.KEY_IMAGE, R.drawable.jacket.toString()) // default value is jacket
        return drawableId?.toInt() ?: R.drawable.jacket // fallback to default value if unable to convert
    }
    private fun isSameImageDisplayedToday(temperature: Int): Boolean {
        val savedDrawableId = getSavedImageDrawableId()
        val currentDrawableId = getClothesImage(temperature)
        return savedDrawableId.equals(currentDrawableId)
    }

    fun setWeatherIcons(imageUrl: List<String>, imageView: ImageView) {
        Glide.with(imageView.context).load(imageUrl[0]).placeholder(R.drawable.ic_download)
            .into(imageView)
    }
}

