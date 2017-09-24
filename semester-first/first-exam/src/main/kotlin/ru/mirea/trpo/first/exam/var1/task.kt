package ru.mirea.trpo.first.exam.var1

import java.io.BufferedReader
import java.io.InputStreamReader

fun readNumbers(n: Int): List<Int> {
    val line = readLine()
    val strings = line!!.split(Regex("\\W"))
    val numbers = strings.map(String::toInt)
    return numbers.take(n)
}

fun readInt(): Int {
    BufferedReader(InputStreamReader(System.`in`)).let {
        while (true) {
            try {
                val input = it.readLine()
                return Integer.parseInt(input)
            } catch (e: NumberFormatException) {
                println("Wrong integer value")
            }
        }
    }
    return 0
}

fun main(args: Array<String>) {
    println("Введите размер массива")
    val n = readInt()
    println("Введите элементы массива")
    val numbers = readNumbers(n)
    val deletions = countDeletions(n, numbers)
    println(deletions)
}

private fun countDeletions(n: Int, numbers: List<Int>): Int {
    val map = numbers
            .groupBy { it }
            .mapValues { it.value.size }
    val mostFrequent = map.maxBy { it.value }!!
    return (n - mostFrequent.value)
}