package ru.mirea.trpo.labs.lab1.var1

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import ru.mirea.trpo.labs.lab1.generate
import ru.mirea.trpo.labs.lab1.getRandomInt
import java.util.*

const val MAX_SIZE = 100
const val MIN_SIZE = 0
const val MAX_VALUE = 1000
const val MIN_VALUE = -1000
const val MAX_SHIFT = 100
const val MIN_SHIFT = -100

@RunWith(Parameterized::class)
class Test(val shiftAlgorithm: (IntArray, Int) -> IntArray) {

    companion object {
        @Parameters
        @JvmStatic
        fun algorithms()
                = listOf(
                ::shiftWithGCD,
                ::shiftWithBlocks,
                ::shiftWithReverses,
                ::shiftInPlace,
                ::shift
        )
    }

    @Test fun testOnStatic() {
        var original = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        var shift = 3
        var array = original.copyOf()
        var expected = intArrayOf(8, 9, 10, 1, 2, 3, 4, 5, 6, 7)
        var actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = -3
        array = original.copyOf()
        expected = intArrayOf(4, 5, 6, 7, 8, 9, 10, 1, 2, 3)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = 12
        array = original.copyOf()
        expected = intArrayOf(9, 10, 1, 2, 3, 4, 5, 6, 7, 8)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = -12
        array = original.copyOf()
        expected = intArrayOf(3, 4, 5, 6, 7, 8, 9, 10, 1, 2)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = 0
        array = original.copyOf()
        expected = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = 10
        array = original.copyOf()
        expected = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        original = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        shift = 3
        array = original.copyOf()
        expected = intArrayOf(7, 8, 9, 1, 2, 3, 4, 5, 6)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = -3
        array = original.copyOf()
        expected = intArrayOf(4, 5, 6, 7, 8, 9, 1, 2, 3)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = 13
        array = original.copyOf()
        expected = intArrayOf(6, 7, 8, 9, 1, 2, 3, 4, 5)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        shift = -13
        array = original.copyOf()
        expected = intArrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4)
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

        original = intArrayOf()

        shift = 100
        array = original.copyOf()
        expected = intArrayOf()
        actual = shiftAlgorithm(array, shift)
        assertArrayEquals(expected, actual)

    }

    @Test fun testOnRandom() {
        (1..100).forEach {
            val (arr_1, arr_2) = generateWithCopy()
            val shift = getRandomInt(MAX_SHIFT, MIN_SHIFT)
            val expected = shift(arr_1, shift)
            val actual = shiftAlgorithm(arr_2, shift)
            assertArrayEquals(expected, actual)
        }

    }

    @Test fun testTime() {
        val times = ArrayList<Long>()
        val original = generate(MAX_SIZE, MIN_SIZE, MAX_VALUE, MIN_VALUE)
        val shift = getRandomInt(MAX_SHIFT, MIN_SHIFT)
        (1..1000000).forEach {
            val array = original.copyOf()
            val start = System.nanoTime()
            shiftAlgorithm(array, shift)
            val end = System.nanoTime()
            times.add(end - start)
        }
        val avg = times.sum() / times.size
        println("[$shiftAlgorithm] ~$avg ns")
    }

    private fun generateWithCopy(): Pair<IntArray, IntArray> {
        val array = generate(MAX_SIZE, MIN_SIZE, MAX_VALUE, MIN_VALUE)
        return Pair(array, array.copyOf())
    }

}