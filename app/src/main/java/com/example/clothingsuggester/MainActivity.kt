package com.example.clothingsuggester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.success.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
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
                // Handle the exception here
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
                            setWeatherIcons(result.current.weather_icons,binding.imageView)
                            setClothesImage(result.current.temperature)
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


    fun setClothesImage(temperature:Int) {
        when (temperature) {
            in -20..15 -> { binding.clothesImage.setImageResource(R.drawable.jacket)}
            in 15..25 -> { binding.clothesImage.setImageResource(R.drawable.sweat) }
            in 25..50 -> { binding.clothesImage.setImageResource(R.drawable.shirt) }
        }
    }
    fun setWeatherIcons(imageUrl: List<String>, imageView: ImageView) {
        Glide.with(imageView.context).load(imageUrl[0]).placeholder(R.drawable.ic_download)
            .into(imageView)
    }
}