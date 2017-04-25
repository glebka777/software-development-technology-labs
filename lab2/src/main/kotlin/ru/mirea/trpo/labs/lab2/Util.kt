@file:Suppress("unused")

package ru.mirea.trpo.labs.lab2

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

fun readFile(path: String): String {
    val file = File(path)
    return file.readText()
}

fun readText(): String {
    println("(Type \"exit\" in newline to exit)")
    BufferedReader(InputStreamReader(System.`in`)).let {
        var input: String? = ""
        var text = ""
        while ({ input = readLine(); input }() != "exit") {
            text += input
        }
        return text
    }
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

fun List<*>.swap(i1: Int, i2: Int) {
    Collections.swap(this, i1, i2)
}

data class Word(val stringValue: String, val frequency: Int) : Comparable<Word> {
    override operator fun compareTo(other: Word): Int {
        return this.frequency - other.frequency
    }

    override fun toString(): String {
        return "('$stringValue' : $frequency)"
    }

}

/**Unused sorts*/
/*******************************************************/

fun List<Word>.bucketSort(quantity: Int): List<Word> {
    val minFrequency = minBy(Word::frequency)!!.frequency
    val maxFrequency = maxBy(Word::frequency)!!.frequency
    val buckets = Array(maxFrequency - minFrequency + 1) { ArrayList<Word>() }
    forEach {
        buckets[it.frequency - minFrequency].add(it)
    }
    val result = ArrayList<Word>(this.size)
    (buckets.size - 1 downTo 0).forEach {
        val bucket = buckets[it]
        if (bucket.size > 0) {
            bucket.forEach {
                result.add(it)
                if (result.size == quantity)
                    return result
            }
        }
    }
    return result
}

fun List<Word>.radixSort(): List<Word> {
    if (isEmpty()) return this
    var max = this.maxBy(Word::frequency)!!.frequency
    var i = 0
    while (max > 0) {
        sortByDigit(this as MutableList<Word>, i)
        i++
        max /= 10
    }
    return this
}

private fun sortByDigit(list: MutableList<Word>, currentDigit: Int) {
    val size = list.size
    val sorted = arrayOfNulls<Word>(size)
    val count = IntArray(10)
    for (i in count.indices) {
        count[i] = 0
    }
    (0 until size)
            .map { list[it].frequency }
            .map { getDigit(it, currentDigit) }
            .forEach { count[it]++ }
    for (i in 1..9) {
        count[i] = count[i] + count[i - 1]
    }
    for (i in size - 1 downTo 0) {
        val number = list[i].frequency
        val d = getDigit(number, currentDigit)
        sorted[count[d] - 1] = list[i]
        count[d]--
    }
    (0 until size).forEach {
        list[it] = sorted[it]!!
    }
}

private fun getDigit(number: Int, currentDigit: Int): Int {
    return number / Math.pow(10.0, currentDigit.toDouble()).toInt() % 10
}