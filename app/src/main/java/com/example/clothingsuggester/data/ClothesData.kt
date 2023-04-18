package com.example.clothingsuggester.data

import com.example.clothingsuggester.R
import com.example.clothingsuggester.util.Constants.AUTUMN_MAX_TEMP
import com.example.clothingsuggester.util.Constants.AUTUMN_MIN_TEMP
import com.example.clothingsuggester.util.Constants.WINTER_MAX_TEMP

class ClothesData {
    private val winterClothes = mutableListOf<Int>()
    private val summerClothes = mutableListOf<Int>()
    private var autumnClothes = mutableListOf<Int>()

    init {
        winterClothes.apply {
            add(0, R.drawable.jacket)
            add(1, R.drawable.jacket_1)
            add(2, R.drawable.jacket_2)
            add(3, R.drawable.jacket_3)
            add(4, R.drawable.jacket_4)
            add(5, R.drawable.jacket_5)
            add(6, R.drawable.jacket_6)
            add(7, R.drawable.jacket_7)
        }

        summerClothes.apply {
            add(0, R.drawable.summer_1)
            add(1, R.drawable.summer_2)
            add(2, R.drawable.summer_3)
            add(3, R.drawable.summer_4)
            add(4, R.drawable.summer_5)
            add(5, R.drawable.summer_6)
            add(6, R.drawable.summer_7)
            add(7, R.drawable.summer_8)
        }

        autumnClothes.apply {
            add(0, R.drawable.autmn)
            add(1, R.drawable.autmn_1)
            add(2, R.drawable.autmn_2)
            add(3, R.drawable.autmn_3)
            add(4, R.drawable.autmn_4)
            add(5, R.drawable.autmn_5)
            add(6, R.drawable.autmn_6)
            add(7, R.drawable.autmn_7)
        }
    }

    fun getClothesImage(temperature: Int): Int {
        return when (temperature) {
            in Int.MIN_VALUE..WINTER_MAX_TEMP -> winterClothes.random()
            in AUTUMN_MIN_TEMP..AUTUMN_MAX_TEMP -> autumnClothes.random()
            else -> summerClothes.random()
        }
    }

}
