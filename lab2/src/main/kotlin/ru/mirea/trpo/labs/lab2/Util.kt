package ru.mirea.trpo.labs.lab2

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

fun readFile(path: String): String {
    val file = File(path)
    return file.readText()
}

fun readFile(file: File): String {
    return file.readText()
}

fun compareWordFrequencies(w1: Word, w2: Word): Int {
    return w1.compareTo(w2)
}

fun readText(): String {
    println("Type \"exit\" in newline to exit.")
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

fun List<Word>.getMostFrequent(quantity: Int): List<Word> {
    val queue = PriorityQueue<Word>()
    forEach {
        if (queue.size >= quantity) {
            if (it.frequency > queue.peek().frequency) {
                queue.poll()
                queue.offer(it)
            }
        } else {
            queue.offer(it)
        }
    }
    return queue.toList()
}

fun List<Word>.quickSelect(quantity: Int): List<Word> {
    val firstMaxFrequency = selectFirstMax(quantity).frequency
    return filter { it.frequency >= firstMaxFrequency }
}

private fun List<Word>.selectFirstMax(quantity: Int): Word {
    (0 until quantity).forEach { i ->
        var maxIndex: Int
        var maxValue = this[i]
        (i until size).forEach { j ->
            if (this[j] > maxValue) {
                maxIndex = j
                maxValue = this[j]
                this.swap(i, maxIndex)
            }
        }
    }
    return this[quantity - 1]
}

private fun List<*>.swap(i1: Int, i2: Int) {
    Collections.swap(this, i1, i2)
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