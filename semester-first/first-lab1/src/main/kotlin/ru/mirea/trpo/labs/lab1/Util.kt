@file:Suppress("unused")

package ru.mirea.trpo.labs.lab1

import java.util.*

fun generate(maxSize: Int, minSize: Int, maxValue: Int, minValue: Int)
        = IntArray(getRandomInt(maxSize, minSize), { getRandomInt(maxValue, minValue) })

fun generateOdd(maxSize: Int, minSize: Int, maxValue: Int, minValue: Int): Pair<Int, IntArray> {
    var size = getRandomInt(maxSize, minSize)
    size = makeOdd(size)
    val list = arrayListOf<Int>()
    (0 until size step 2).forEach {
        val element = getRandomInt(maxValue, minValue)
        list.add(element)
        list.add(element)
    }
    val singleElement = getRandomInt(maxValue, minValue)
    list.add(singleElement)
    Collections.shuffle(list)
    val array = list.toIntArray()
    return Pair(singleElement, array)
}

fun IntArray.print() {
    println(Arrays.toString(this))
}

fun IntArray.swap(i1: Int, i2: Int) {
    val tmp = this[i1]
    this[i1] = this[i2]
    this[i2] = tmp
}

fun IntArray.swap(i1: Int, i2: Int, len: Int) {
    val size = this.size
    (0 until len)
            .takeWhile { i2 + it < size }
            .forEach { swap(i1 + it, i2 + it) }
}

fun IntArray.reverse(first: Int, last: Int) {
    val midPoint = (first + last) / 2
    if (midPoint < 0) return
    var reverseIndex = last
    (first..midPoint).forEach { index ->
        swap(index, reverseIndex)
        reverseIndex--
    }
}

fun getRandomInt(max: Int, min: Int) = Random().nextInt(max + 1 - min) + min

private fun makeOdd(value: Int) = if (value % 2 == 0) value + 1 else value