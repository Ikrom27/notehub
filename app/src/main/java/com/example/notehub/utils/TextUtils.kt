package com.example.notehub.utils

fun isValidInput(input: String): Boolean {
    val regex = Regex("[a-zA-Z0-9\\s\\p{So}]+")
    return input.matches(regex)
}

fun String.addPath(child: String): String {
    return "$this/$child"
}

fun String.removeLastPath(): String {
    var i = this.length
    while (this[i] != '/'){
        i--
    }
    if (i > 0){
        return this.substring(0, i)
    } else {
        return this
    }
}