package com.example.clothingsuggester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.clothingsuggester.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val client = OkHttpClient()
    val LOG_TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.get.setOnClickListener {
            getRequestUsingOkHttp()
        }
    }
    private fun getRequestUsingOkHttp() {
        val country = binding.country.text.toString()
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegment("current")
            .addQueryParameter("access_key", "YOUR_API_KEY")
            .addQueryParameter("query", country)
            .build()

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    binding.textView.text = e.message.toString()
                    Log.d(LOG_TAG,e.message.toString())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    binding.textView.text = response.body?.string().toString()
                    Log.e(LOG_TAG,response.body?.string().toString())
                }
            }
        })
    }
}