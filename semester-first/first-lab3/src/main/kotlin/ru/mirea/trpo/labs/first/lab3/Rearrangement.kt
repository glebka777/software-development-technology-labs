package ru.mirea.trpo.labs.first.lab3

import java.util.*
import kotlin.collections.ArrayList

@Suppress("unused")
class Rearrangement(vararg val array: Int) {

    companion object {

        const val LAUNCHING_TIMES = 1000000

        private fun getOrdered(length: Int): IntArray {
            return IntArray(length) { it + 1 }
        }

        fun random(length: Int): IntArray {
            if (length < 0)
                throw IllegalArgumentException("Negative length")
            val random = Random()
            val array = getOrdered(length)
            if (length == 1)
                return array
            array.forEachIndexed { index, _ ->
                val randomIndex = random.nextInt(index + 1)
                array.swap(index, randomIndex)
            }
            return array
        }

        fun formStatistics(length: Int): Array<IntArray> {
            val permutations = ArrayList<IntArray>(LAUNCHING_TIMES)
            (0..LAUNCHING_TIMES - 1).forEach {
                permutations.add(random(length))
            }
            val table = Array(length) { IntArray(length) }
            permutations.forEach {
                it.forEachIndexed { index, elem ->
                    table[index][elem - 1]++
                }
            }
            return table
        }

    }

    fun check(): Boolean {
        val ordered = getOrdered(array.size)
        try {
            array.forEach { ordered[it - 1] = -1 }
        } catch(e: ArrayIndexOutOfBoundsException) {
            return false
        }
        ordered.forEach {
            if (it != -1)
                return false
        }
        return true
    }

    fun next(): IntArray? {
        if (!check())
            throw IllegalArgumentException("Not a permutation")
        val size = array.size
        var currentIndex = size - 2
        while (currentIndex != -1 && array[currentIndex] > array[currentIndex + 1])
            currentIndex--
        if (currentIndex == -1)
            return null
        val newArray = array.copyOf()
        var minMaxIndex = size - 1
        while (array[currentIndex] > array[minMaxIndex])
            minMaxIndex--
        newArray.swap(currentIndex, minMaxIndex)
        var leftIndex = currentIndex + 1
        var rightIndex = size - 1
        while (leftIndex < rightIndex) {
            newArray.swap(leftIndex, rightIndex)
            leftIndex++
            rightIndex--
        }
        return newArray
    }

    fun rest(): List<IntArray> {
        val permutations = ArrayList<IntArray>()
        var next = this.next() ?: return permutations
        do {
            permutations.add(next)
            val newNext = Rearrangement(*next).next() ?: return permutations
            next = newNext
        } while (true)
    }

    fun random(): IntArray {
        return random(array.size)
    }

}