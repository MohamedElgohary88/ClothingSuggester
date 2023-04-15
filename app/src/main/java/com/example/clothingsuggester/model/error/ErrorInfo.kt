package com.example.clothingsuggester.model.error

data class ErrorInfo(
    val code: Int,
    val type: String,
    val info: String
)