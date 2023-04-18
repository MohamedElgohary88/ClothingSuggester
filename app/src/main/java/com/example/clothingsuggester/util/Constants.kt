package com.example.clothingsuggester.util

import com.example.clothingsuggester.BuildConfig
import okhttp3.HttpUrl

object Constants {
    fun getUrl(country: String) = HttpUrl.Builder()
        .scheme("http")
        .host("api.weatherstack.com")
        .addPathSegment("current")
        .addQueryParameter("query", country)
        .build()

    const val MY_SHARED = "My_SHARED"
    const val KEY_IMAGE = "Key_Image"
    const val KEY_DATE = "KEY_DATE"

    const val WINTER_MAX_TEMP = 15
    const val AUTUMN_MIN_TEMP = 15
    const val AUTUMN_MAX_TEMP = 25
}