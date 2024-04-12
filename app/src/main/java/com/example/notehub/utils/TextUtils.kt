package com.example.notehub.utils

fun isValidInput(input: String): Boolean {
    val regex = Regex("[a-zA-Z0-9\\s\\p{So}]+")
    return input.matches(regex)
}