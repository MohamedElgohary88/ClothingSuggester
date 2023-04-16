package com.example.clothingsuggester.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.clothingsuggester.R
import com.example.clothingsuggester.view.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SharedPreferences(private val context: Context) {
    var clothesData: ClothesData = ClothesData()

    fun saveImage(temperature: Int) {
        val drawableId = clothesData.getClothesImage(temperature)
        val saveShared =
            context.getSharedPreferences(Constants.SHARED_IMAGE, Context.MODE_PRIVATE)
        val editor = saveShared.edit()
        editor.putInt(Constants.KEY_IMAGE, drawableId)
        editor.putBoolean(Constants.KEY_IMAGE_SAVED, true)
        editor.apply()
    }

    fun getSavedImage(): Int {
        val getShared =
            context.getSharedPreferences(Constants.SHARED_IMAGE, Context.MODE_PRIVATE)
        return getShared.getInt(Constants.KEY_IMAGE, R.drawable.jacket)
    }

    fun checkImageSaved(): Boolean {
        val sharedPreferences = context.getSharedPreferences(Constants.KEY_IMAGE_SAVED, Context.MODE_PRIVATE)
        return sharedPreferences.all.isEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDate(apiDate: String): Boolean {
        val localeDate = getLocalDate()
        return localeDate == apiDate.take(10)
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun getLocalDate(): String {
        val currentDate = LocalDate.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(dateFormatter)
    }

}