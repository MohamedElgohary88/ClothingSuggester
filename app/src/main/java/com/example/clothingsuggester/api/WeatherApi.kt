package com.example.clothingsuggester.api

import com.example.clothingsuggester.response.success.WeatherData
import com.example.clothingsuggester.util.Constants
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException

class WeatherApi {

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun getWeatherData(country: String, callback: (WeatherData?, Throwable?) -> Unit) {
        val url = Constants.getUrl(country)
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    response.body?.string()?.let { jsonString ->
                        val result = Gson().fromJson(jsonString, WeatherData::class.java)
                        callback(result, null)
                    }
                } catch (e: Exception) {
                    callback(null, e)
                }
            }
        })
    }
}