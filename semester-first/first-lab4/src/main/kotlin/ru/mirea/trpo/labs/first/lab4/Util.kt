package ru.mirea.trpo.labs.first.lab4

import java.io.File
import java.util.*

fun readFile(path: String): String {
    val file = File(path)
    return file.readText()
}

fun Random.nextAZChar(): Char {
    return (nextInt(26) + 'a'.toInt()).toChar()
}