package com.example.clothingsuggester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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
            getRequestUsingOkHttp(binding.country.query.toString())
        }
    }

    private fun getRequestUsingOkHttp(country :String) {
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
                    // Log.d(LOG_TAG,e.message.toString())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString,WeatherData::class.java)
                    Log.i(LOG_TAG, result.toString())
                    runOnUiThread {
                        binding.textTemperature.text = result.current.temperature.toString()
                        binding.textStatus.text = result.current.weather_descriptions.toString()
                        binding.textCountry.text = result.location.name
                        setWeatherIcons(result.current.weather_icons.toString(),binding.imageView)
                    }
                }
            }
        })
    }
    fun setWeatherIcons(imageUrl: String, imageView: ImageView) {
        Glide.with(imageView.context).load(imageUrl).placeholder(R.drawable.ic_download)
            .into(imageView)
    }
}