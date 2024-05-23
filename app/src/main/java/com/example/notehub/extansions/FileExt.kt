package com.example.notehub.extansions

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.BufferedReader

fun File.readPreview(charCount: Int = 64): String {
    val buffer = CharArray(charCount)
    FileInputStream(this).use { fis ->
        InputStreamReader(fis).use { isr ->
            val reader = BufferedReader(isr)
            val charsRead = reader.read(buffer, 0, charCount)
            return if (charsRead != -1) {
                String(buffer, 0, charsRead)
            } else {
                ""
            }
        }
    }
}

fun File.getNameWithoutExtension(): String {
    val fileName = this.name
    val lastDotIndex = fileName.lastIndexOf('.')
    return if (lastDotIndex > 0) {
        fileName.substring(0, lastDotIndex)
    } else {
        fileName
    }
}