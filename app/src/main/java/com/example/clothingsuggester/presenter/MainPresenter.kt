package com.example.clothingsuggester.presenter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.clothingsuggester.R
import com.example.clothingsuggester.model.success.WeatherData
import com.example.clothingsuggester.util.Constants
import com.example.clothingsuggester.view.MainView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainPresenter {
    private val client = OkHttpClient.Builder().build()
    lateinit var view: MainView
    fun getRequestUsingOkHttp(country: String) {
        val url = Constants.getUrl(country)
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                view.onFailure()
            }
            override fun onResponse(call: Call, response: Response) {
                try {
                    response.body?.string()?.let { jsonString ->
                        val result = Gson().fromJson(jsonString, WeatherData::class.java)
                        view.setData(result)
                    }
                } catch (e: Exception) {
                }
            }
        })
    }
}