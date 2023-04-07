package com.example.clothingsuggester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clothingsuggester.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.get.setOnClickListener {
            getRequestUsingOkHttp()
        }
    }
    private fun getRequestUsingOkHttp() {
        val request = Request.Builder().url("").build()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    binding.textView.text = e.message.toString()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    binding.textView.text = response.body?.string().toString()
                }
            }
        })
    }
}