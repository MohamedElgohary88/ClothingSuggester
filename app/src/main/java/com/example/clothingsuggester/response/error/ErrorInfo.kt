package com.example.clothingsuggester.response.error

data class ErrorInfo(
    val code: Int,
    val type: String,
    val info: String
)