package ru.mirea.trpo.labs.lab4

fun findCommonChars(text: String): CharArray {
    val strings = text.split(Regex("\\n")).filter { !it.isNullOrBlank() }
    val chars = strings.map { it.toCharArray().toSet() }.reduce { acc, next -> acc.intersect(next) }
    return chars.toCharArray()
}

fun main(args: Array<String>) {
    val path = String::class.java.getResource("/text/sample_2.txt").path
    val text = readFile(path)
    val chars = findCommonChars(text)
    println(chars)
}