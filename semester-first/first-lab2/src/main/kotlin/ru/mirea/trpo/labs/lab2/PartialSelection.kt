package ru.mirea.trpo.labs.lab2

fun <E : Comparable<E>> List<E>.selectionSort(quantity: Int): List<E> {
    select(quantity)
    return take(quantity)
}

private fun <E : Comparable<E>> List<E>.select(quantity: Int): E {
    (0 until quantity).forEach { i ->
        var maxIndex: Int
        var maxValue = this[i]
        (i until size).forEach { j ->
            if (this[j] > maxValue) {
                maxIndex = j
                maxValue = this[j]
                this.swap(i, maxIndex)
            }
        }
    }
    return this[quantity - 1]
}