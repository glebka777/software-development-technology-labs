@file:Suppress("unused")

package ru.mirea.trpo.labs.lab3

fun IntArray.swap(i1: Int, i2: Int) {
    val temp = this[i1]
    this[i1] = this[i2]
    this[i2] = temp
}

fun IntArray.toPrint(): String {
    val sb = StringBuilder()
    forEach { sb.append("$it ") }
    return sb.toString().trim()
}

fun Array<IntArray>.toPrint(): String {
    val sb = StringBuilder()
    forEach {
        it.forEach { sb.append("$it ") }
        sb.append("\n")
    }
    return sb.toString().trim()
}


fun main(args: Array<String>) {
    println(Rearrangement(1,2,3).next())
}