package ru.mirea.trpo.labs.first.lab5.test

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.mirea.trpo.labs.first.lab5.*

const val VALID_EMAIL = "100test@test.company.ru"
const val VALID_TIME_1 = "00:12:59"
const val VALID_TIME_2 = "23:59"
const val INVALID_TIME_1 = "24:59"
const val INVALID_TIME_2 = "23:60"
const val VALID_DATE_1 = "2010-10-05"
const val VALID_DATE_2 = "1234-12-04"
const val VALID_DATETIME_1 = "1234-12-04 00:00"
const val VALID_DATETIME_2 = "00:00 1234-12-04"
const val VALID_DOMAIN_1 = "kek.com"
const val VALID_DOMAIN_2 = "hello.there.travel"

class Test {

    @Test fun testEmail() {
        assertTrue(checkEmail(VALID_EMAIL))
    }

    @Test fun testTime() {
        assertTrue(checkTime(VALID_TIME_1))
        assertTrue(checkTime(VALID_TIME_2))
        assertFalse(checkTime(INVALID_TIME_1))
        assertFalse(checkTime(INVALID_TIME_2))
    }

    @Test fun testDate() {
        assertTrue(checkDate(VALID_DATE_1))
        assertTrue(checkDate(VALID_DATE_2))
    }

    @Test fun testDateAndTime() {
        assertTrue(checkDateAndTime(VALID_DATETIME_1))
        assertTrue(checkDateAndTime(VALID_DATETIME_2))
    }

    @Test fun testDomain() {
        assertTrue(checkDomain(VALID_DOMAIN_1))
        assertTrue(checkDomain(VALID_DOMAIN_2))
    }

}