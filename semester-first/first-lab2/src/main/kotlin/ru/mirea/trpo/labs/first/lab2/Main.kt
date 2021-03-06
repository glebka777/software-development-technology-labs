package ru.mirea.trpo.labs.first.lab2


fun countWords(text: String): List<Word>
        = text.split(Regex("\\W+"))
        .map(String::trim)
        .filter { !it.matches(Regex("")) }
        .groupBy { it }
        .mapValues { it.value.size }
        .map { Word(it.key, it.value) }


fun getMostFrequentByJavaSort(words: List<Word>, quantity: Int = words.size)
        = words.sortedDescending().take(quantity).map(Word::stringValue)


fun getMostFrequentByQueue(words: List<Word>, quantity: Int = words.size)
        = words.getMostFrequent(quantity).map(Word::stringValue)


fun getMostFrequentByPartialSelectionSort(words: List<Word>, quantity: Int = words.size)
        = words.selectionSort(quantity).map(Word::stringValue)


fun getMostFrequentByQuickSelectPartitionSort(words: List<Word>, quantity: Int = words.size)
        = words.quickSelectPartitionSort(quantity).map(Word::stringValue)


fun main(args: Array<String>) {
    println("Enter text:")
    val text = readText()
    println("Enter number of words:")
    val quantity = readInt()
    val words = countWords(text)
    val mostFrequent = getMostFrequentByPartialSelectionSort(words, quantity)
    println()
    println(mostFrequent)
}