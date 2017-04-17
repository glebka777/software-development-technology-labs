package ru.mirea.trpo.labs.lab4.test
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.mirea.trpo.labs.lab4.findCommonChars
import ru.mirea.trpo.labs.lab4.nextAZChar
import ru.mirea.trpo.labs.lab4.readFile
import java.util.*

const val SAMPLE_1_PATH = "/text/sample_1.txt"
const val SAMPLE_2_PATH = "/text/sample_2.txt"
const val COMMON_CHARS_MAX_LENGTH = 3
const val STRING_MAX_LENGTH = 10
const val STRINGS_SIZE = 1000000

class Test {

    @Test fun sampleTest_1() {
        val path = Test::class.java.getResource(SAMPLE_1_PATH).path
        val text = readFile(path)
        val chars = findCommonChars(text)
        assertArrayEquals(charArrayOf('A', 'B', 'C'), chars.sortedArray())
    }

    @Test fun sampleTest_2() {
        val path = Test::class.java.getResource(SAMPLE_2_PATH).path
        val text = readFile(path)
        val chars = findCommonChars(text)
        assertArrayEquals(charArrayOf('A', 'B'), chars.sortedArray())
    }

    @Test fun hugeTest() {
        val random = Random()
        val commonCharsSize = random.nextInt(COMMON_CHARS_MAX_LENGTH) + 1
        val stringSize = random.nextInt(STRING_MAX_LENGTH) + 1
        val commonChars = CharArray(commonCharsSize) { random.nextAZChar() }
        val textSB = StringBuilder()
        (0 until STRINGS_SIZE).forEach {
            textSB.append(commonChars)
            (0 until stringSize).forEach {
                textSB.append(random.nextAZChar())
            }
            textSB.appendln()
        }
        val actualCommonChars = findCommonChars(textSB.toString().trim())
        commonChars.forEach {
            assertTrue(actualCommonChars.contains(it))
        }
    }

}