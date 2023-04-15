package com.example.clothingsuggester.data

import com.example.clothingsuggester.R

class ClothesData {
    var winterClothes = mutableListOf<Int>()
    var summerClothes = mutableListOf<Int>()
    var autumnClothes = mutableListOf<Int>()

    init {
        winterClothes.add(0, R.drawable.jacket)
        winterClothes.add(1, R.drawable.jacket_1)
        winterClothes.add(2, R.drawable.jacket_2)
        winterClothes.add(3, R.drawable.jacket_3)
        winterClothes.add(4, R.drawable.jacket_4)
        winterClothes.add(5, R.drawable.jacket_5)
        winterClothes.add(6, R.drawable.jacket_6)
        winterClothes.add(7,R.drawable.jacket_7)


        summerClothes.add(0, R.drawable.summer_1)
        summerClothes.add(1, R.drawable.summer_2)
        summerClothes.add(2, R.drawable.summer_3)
        summerClothes.add(3, R.drawable.summer_4)
        summerClothes.add(4, R.drawable.summer_5)
        summerClothes.add(5, R.drawable.summer_6)
        summerClothes.add(6, R.drawable.summer_7)
        summerClothes.add(7, R.drawable.summer_8)

        autumnClothes.add(0, R.drawable.autmn)
        autumnClothes.add(1, R.drawable.autmn_1)
        autumnClothes.add(2, R.drawable.autmn_2)
        autumnClothes.add(3, R.drawable.autmn_3)
        autumnClothes.add(4, R.drawable.autmn_4)
        autumnClothes.add(5, R.drawable.autmn_5)
        autumnClothes.add(6, R.drawable.autmn_6)
        autumnClothes.add(7, R.drawable.autmn_7)

    }
}
