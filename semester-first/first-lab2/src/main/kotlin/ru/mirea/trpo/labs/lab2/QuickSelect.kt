package ru.mirea.trpo.labs.lab2

import java.util.*

fun <E : Comparable<E>> List<E>.quickSelectPartitionSort(quantity: Int): List<E> {
    quickSelect(quantity - 1)
    return take(quantity)
}

internal fun <E : Comparable<E>> List<E>.partition(left: Int, right: Int, pivot: Int): Int {
    val pivotElem = this[pivot]
    swap(pivot, right)
    var store = left
    (left until right).forEach {
        if (this[it] > pivotElem) {
            swap(it, store)
            store++
        }
    }
    swap(right, store)
    return store
}

private fun <E : Comparable<E>> List<E>.quickSelect(quantity: Int): E {
    var left = 0
    var right = size - 1
    val random = Random()
    while (true) {
        if (left == right)
            return this[left]
        var pivot = random.nextInt(right - left + 1) + left
        pivot = partition(left, right, pivot)
        if (quantity == pivot)
            return this[quantity]
        else if (quantity < pivot)
            right = pivot - 1
        else
            left = pivot + 1
    }
}
