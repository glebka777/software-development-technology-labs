package ru.mirea.trpo.labs.lab1.var1

import ru.mirea.trpo.labs.lab1.reverse
import ru.mirea.trpo.labs.lab1.swap

fun shift(array: IntArray, shift: Int): IntArray {
    val size = array.size
    val shifted = array.copyOf()
    (0 until size).forEach {
        var newIndex = (it + shift) % size
        if (newIndex < 0) newIndex += size
        shifted[newIndex] = array[it]
    }
    return shifted
}

fun shiftInPlace(array: IntArray, shift: Int): IntArray {
    var shft = resolveShift(shift, array.size)
    if (shft == 0) return array
    val size = array.size
    shft = size - shft
    var start = 0
    var startVal = array[start]
    var current = start
    var next: Int
    for (it in 1..size) {
        next = (current + shft) % size
        if (next == start) {
            array[current] = startVal
            start++
            startVal = array[start]
            current = start
        } else {
            array[current] = array[next]
            current = next
        }
    }
    return array
}

fun shiftWithReverses(array: IntArray, shift: Int): IntArray {
    var shft = resolveShift(shift, array.size)
    if (shft == 0) return array
    val size = array.size
    val last = size - 1
    shft = size - shft
    with(array) {
        reverse(0, shft - 1)
        reverse(shft, last)
        reverse(0, last)
    }
    return array
}

fun shiftWithGCD(array: IntArray, shift: Int): IntArray {
    var shft = resolveShift(shift, array.size)
    if (shft == 0) return array
    val size = array.size
    val gcd = gcd(size, shft)
    shft = size - shft
    (0 until gcd).forEach {
        val currentValue = array[it]
        var current = it
        while (true) {
            val next = (current + shft) % size
            if (next == it)
                break
            array[current] = array[next]
            current = next
        }
        array[current] = currentValue
    }
    return array
}

fun shiftWithBlocks(array: IntArray, shift: Int): IntArray {
    var shft = resolveShift(shift, array.size)
    if (shft == 0) return array
    val size = array.size
    shft = size - shft
    var i = shft
    var j = size - shft
    while (i != j) {
        if (i > j) {
            array.swap(shft - i, shft, j)
            i -= j
        } else {
            array.swap(shft - i, shft + j - i, i)
            j -= i
        }
    }
    array.swap(shft - i, shft, i)
    return array
}

private fun resolveShift(shift: Int, size: Int): Int {
    if (size == 0 || size == 1 || size == shift) return 0
    var newShift = shift
    if (Math.abs(shift) > size) {
        newShift %= size
    }
    if (newShift < 0)
        newShift += size
    return newShift
}

private fun gcd(i1: Int, i2: Int): Int {
    var a = i1
    var b = i2
    while (a != b) {
        if (a > b)
            a -= b
        else
            b -= a
    }
    return a
}