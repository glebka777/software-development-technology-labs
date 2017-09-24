package ru.mirea.trpo.labs.first.lab2

import java.util.*

fun <E : Comparable<E>> List<E>.getMostFrequent(quantity: Int): List<E> {
    val queue = PriorityQueue<E>()
    forEach {
        if (queue.size >= quantity) {
            if (it > queue.peek()) {
                queue.poll()
                queue.offer(it)
            }
        } else {
            queue.offer(it)
        }
    }
    return queue.toList()
}