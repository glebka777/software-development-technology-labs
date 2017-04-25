package ru.mirea.trpo.labs.lab2


fun countWords(text: String): List<Word>
        = text.split(Regex("\\W+"))
        .map(String::trim)
        .filter { !it.matches(Regex("")) }
        .groupBy { it }
        .mapValues { it.value.size }
        .map { Word(it.key, it.value) }


fun getMostFrequentByRadix(words: List<Word>, quantity: Int = words.size)
        = words.radixSort().take(quantity).map(Word::stringValue)


fun getMostFrequentByJavaSort(words: List<Word>, quantity: Int = words.size)
        = words.sortedDescending().take(quantity).map(Word::stringValue)


fun getMostFrequentByConvertingToSet(words: List<Word>, quantity: Int = words.size)
        = words.toSortedSet().take(quantity).map(Word::stringValue)


fun getMostFrequentByBucketSort(words: List<Word>, quantity: Int = words.size)
        = words.bucketSort(quantity).map(Word::stringValue)


fun getMostFrequentByQueue(words: List<Word>, quantity: Int = words.size)
        = words.getMostFrequent(quantity).map(Word::stringValue)


fun getMostFrequentByPartialSelectionSort(words: List<Word>, quantity: Int = words.size)
        = words.selectionSort(quantity).map(Word::stringValue)

fun getMostFrequentByQuickSelectPartitionSort(words: List<Word>, quantity: Int = words.size)
        = words.quickSelectPartitionIterativeSort(quantity).map(Word::stringValue)

data class Word(val stringValue: String, val frequency: Int): Comparable<Word> {
    override operator fun compareTo(other: Word): Int {
        return this.frequency - other.frequency
    }
}

fun main(args: Array<String>) {
    val text = readText()
    val quantity = readInt()
    val words = countWords(text)
    val mostFrequent = getMostFrequentByPartialSelectionSort(words, quantity)
    println()
    println(mostFrequent)
}