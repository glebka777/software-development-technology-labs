package ru.mirea.trpo.labs.lab2.test

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import ru.mirea.trpo.labs.lab2.*
import java.util.*

const val TEST_TEXT_1 = "/text/sample_1.txt"
const val TEST_TEXT_2 = "/text/sample_2.txt"

const val LAUNCHING_TIMES = 1000000

const val WORDS_QUANTITY = 6

@JvmField val EXPECTED = listOf("aaa", "ccc", "bbb", "qwe", "22", "1").take(WORDS_QUANTITY).sorted().toTypedArray()

@RunWith(Parameterized::class)
class Test(val algorithm: (List<Word>, Int) -> List<String>) {

    companion object {
        @Parameters
        @JvmStatic
        fun algorithms()
                = listOf(
                ::getMostFrequentByQueue,
                ::getMostFrequentByJavaSort,
                ::getMostFrequentByPartialSelectionSort,
                ::getMostFrequentByQuickSelectPartitionSort,
                ::getMostFrequentByMedianOfMediansSort
        )
    }

    @Test fun test1() {
        val times = ArrayList<Long>()
        val words = countWords(readFile(Test::class.java.getResource(TEST_TEXT_1).path))
        (1..LAUNCHING_TIMES).forEach {
            val start = System.nanoTime()
            val actual = algorithm(words, WORDS_QUANTITY)
            val end = System.nanoTime()
            times.add(end - start)
            Assert.assertArrayEquals(EXPECTED, actual.toTypedArray().sortedArray())
        }
        val avg = times.sum() / times.size
        print(avg, algorithm, words.size)
    }

    @Test fun test2() {
        val times = ArrayList<Long>()
        val words = countWords(readFile(Test::class.java.getResource(TEST_TEXT_2).path))
        (1..LAUNCHING_TIMES).forEach {
            val start = System.nanoTime()
            val actual = algorithm(words, WORDS_QUANTITY)
            val end = System.nanoTime()
            times.add(end - start)
            Assert.assertArrayEquals(EXPECTED, actual.toTypedArray().sortedArray())
        }
        val avg = times.sum() / times.size
        print(avg, algorithm, words.size)
    }

    private fun formatAlgName(algOutput: String): String {
        return algOutput
                .replace("function getMostFrequentBy", "")
                .replace(" (Kotlin reflection is not available)", "")
    }

    private fun print(time: Long, algorithm: (List<Word>, Int) -> List<String>, size: Int) {
        val formattedAlg = "%25s".format(formatAlgName(algorithm.toString()))
        val formattedAllWords = "%-3s %3s".format("N:", "$size")
        val formattedChosenWords = "%-3s %1s".format("K:", "${WORDS_QUANTITY}")
        val formattedTime = "%9s".format("~$time ns")
        println("[$formattedAlg] [$formattedAllWords] [$formattedChosenWords] $formattedTime")
    }
}