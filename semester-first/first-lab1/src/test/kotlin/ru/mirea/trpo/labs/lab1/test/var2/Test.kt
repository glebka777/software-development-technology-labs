package ru.mirea.trpo.labs.lab1.test.var2

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.mirea.trpo.labs.lab1.generateOdd
import ru.mirea.trpo.labs.lab1.var2.findSingle

const val MAX_SIZE = 999999
const val MIN_SIZE = 1
const val MAX_VALUE = 1000000000
const val MIN_VALUE = 1

class Test {

    @Test fun testFindSingle() {
        for (it in 1..10) {
            val (singleExpected, array) = generateOdd(MAX_SIZE, MIN_SIZE, MAX_VALUE, MIN_VALUE)
            val singleActual = findSingle(array)
            assertEquals(singleExpected, singleActual)
        }
    }

}