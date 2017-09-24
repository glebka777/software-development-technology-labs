package ru.mirea.trpo.labs.lab3.test
import org.junit.Assert.*
import org.junit.Test
import ru.mirea.trpo.labs.lab3.Rearrangement

class RearrangementTest {

    @Test fun testNext() {
        assertArrayEquals(Rearrangement(1,2,3).next(), intArrayOf(1,3,2))
        assertArrayEquals(Rearrangement(1,3,2).next(), intArrayOf(2,1,3))
        assertArrayEquals(Rearrangement(2,1,3).next(), intArrayOf(2,3,1))
        assertArrayEquals(Rearrangement(2,3,1).next(), intArrayOf(3,1,2))
        assertArrayEquals(Rearrangement(3,1,2).next(), intArrayOf(3,2,1))
    }

    @Test fun testCheck(){
        assertTrue(Rearrangement(1,2,3,4,5).check())
        assertTrue(Rearrangement(3,2,1,4,5).check())
        assertFalse(Rearrangement(3,3,3).check())
        assertFalse(Rearrangement(1,2,4,6).check())
    }

}