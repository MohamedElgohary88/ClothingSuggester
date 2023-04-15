package com.example.clothingsuggester.view.util

import com.example.clothingsuggester.BuildConfig
import okhttp3.HttpUrl

object Constants {
    fun getUrl(country: String) = HttpUrl.Builder()
        .scheme("http")
        .host("api.weatherstack.com")
        .addPathSegment("current")
        .addQueryParameter("access_key", BuildConfig.API_KEY)
        .addQueryParameter("query", country)
        .build()

    const val SHARED_IMAGE = "My_IMAGE"
    const val KEY_IMAGE = "Key_Image"

    const val SHARED_DATE = "My_DATE"
    const val KEY_DATE = "Key_DATE"
}