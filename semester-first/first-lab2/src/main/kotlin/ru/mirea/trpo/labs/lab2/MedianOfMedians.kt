@file:Suppress("unused")

package ru.mirea.trpo.labs.lab2

fun <E : Comparable<E>> List<E>.medianOfMediansSort(quantity: Int): List<E> {
    medianOfMediansSelect(0, size - 1, quantity - 1)
    return take(quantity)
}

private fun <E : Comparable<E>> List<E>.medianOfMediansSelect(left: Int, right: Int, quantity: Int): Int {
    var localLeft = left
    var localRight = right
    while (true) {
        if (localLeft == localRight)
            return localLeft
        var pivot = pivot(localLeft, localRight)
        pivot = partition(localLeft, localRight, pivot)
        if (quantity == pivot)
            return quantity
        else if (quantity < pivot)
            localRight = pivot - 1
        else
            localLeft = pivot + 1
    }
}

private fun <E : Comparable<E>> List<E>.pivot(left: Int, right: Int): Int {
    if (right - left < 5)
        return partitionFive(left, right)
    (left until (right + 1) step 5).forEach {
        var subRight = it + 4
        if (subRight > right)
            subRight = right
        val medianFive = partitionFive(it, subRight)
        swap(medianFive, left + (it - left) / 5)
    }
    return medianOfMediansSelect(
            left,
            left + (right - left + 4) / 5 - 1,
            left + (right - left) / 10
    )
}

private fun <E : Comparable<E>> List<E>.partitionFive(left: Int, right: Int): Int {
    (left until right - 1).forEach { i ->
        (i + 1 until right).forEach { j ->
            if (this[i] < this[j])
                swap(i, j)
        }
    }
    return (left + right) / 2
}