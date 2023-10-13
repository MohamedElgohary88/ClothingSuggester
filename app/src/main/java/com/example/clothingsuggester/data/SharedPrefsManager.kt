package com.example.clothingsuggester.data

import android.content.Context
import com.example.clothingsuggester.util.Constants

class SharedPrefsManager(private val applicationContext: Context) {
    private val sharedPreferences =
        applicationContext.getSharedPreferences(Constants.MY_SHARED, Context.MODE_PRIVATE)

    fun saveImage(drawableId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.KEY_IMAGE, drawableId)
        editor.apply()
    }

    fun saveDate(date: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.KEY_DATE,date)
        editor.apply()
    }

    fun getSavedDate(): String? {
        return sharedPreferences.getString(Constants.KEY_DATE, "")
    }

    fun getSavedImage(): Int {
        return sharedPreferences.getInt(Constants.KEY_IMAGE, 0)
    }

    fun isImageSaved(): Boolean {
        return sharedPreferences.contains(Constants.KEY_IMAGE)
    }

}
