package ru.mirea.trpo.labs.lab5

const val DATE_PATTERN =
        "^[0-9]{4}" +
                "([-\\./])" +
                "((0[1-9]|1[012])(\\1)(0[1-9]|[12]\\d)|(0[13-9]|1[012])" +
                "(\\1)" +
                "30|(0[13578]|1[02])(\\1)31)$"

const val TIME_PATTERN = "^([0-1]\\d|2[0-3])(:[0-5]\\d){1,2}$"

const val DOMAIN_PATTERN = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$"

@JvmField val EMAIL_PATTERN = "^[-\\w.]+@${DOMAIN_PATTERN.substring(1, DOMAIN_PATTERN.length - 1)}$"

const val TWO_WORDS_PATTERN = "(\\w+)\\s+(\\w+)"


fun checkDate(string: String) = Regex(DATE_PATTERN).matches(string)

fun checkTime(string: String) = Regex(TIME_PATTERN).matches(string)

fun checkDateAndTime(string: String): Boolean {
    val (date, time) = string.split(" ")
    return checkDate(date) && checkTime(time) || checkDate(time) && checkTime(date)
}

fun checkEmail(string: String): Boolean = Regex(EMAIL_PATTERN).matches(string)

fun checkDomain(string: String): Boolean = Regex(DOMAIN_PATTERN).matches(string)

fun swapTwoWords(string: String) = Regex(TWO_WORDS_PATTERN).replace(string, "$2 $1")

fun main(args: Array<String>) {
    println(swapTwoWords("abc def"))
}